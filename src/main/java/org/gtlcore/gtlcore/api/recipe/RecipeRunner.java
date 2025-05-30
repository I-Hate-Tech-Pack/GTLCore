package org.gtlcore.gtlcore.api.recipe;

import org.gtlcore.gtlcore.api.machine.trait.IDistinctMachine;

import com.gregtechceu.gtceu.api.capability.recipe.*;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.api.recipe.chance.boost.ChanceBoostFunction;
import com.gregtechceu.gtceu.api.recipe.chance.logic.ChanceLogic;
import com.gregtechceu.gtceu.api.recipe.content.Content;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;

import com.google.common.collect.Table;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;

import java.util.*;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class RecipeRunner {

    private final GTRecipe recipe;
    private final IO io;
    private final boolean isTick;
    private final IRecipeCapabilityHolder holder;
    private final Map<RecipeCapability<?>, Object2IntMap<?>> chanceCaches;
    private final Table<IO, RecipeCapability<?>, List<IRecipeHandler<?>>> capabilityProxies;
    private final boolean simulated;

    private RecipeCapability<?> capability;
    private Set<IRecipeHandler<?>> used;
    private RecipeRunner.ContentSlots content;
    private RecipeRunner.ContentSlots search;

    public RecipeRunner(GTRecipe recipe, IO io, boolean isTick,
                        IRecipeCapabilityHolder holder, Map<RecipeCapability<?>, Object2IntMap<?>> chanceCaches, boolean simulated) {
        this.recipe = recipe;
        this.io = io;
        this.isTick = isTick;
        this.holder = holder;
        this.chanceCaches = chanceCaches;
        this.capabilityProxies = holder.getCapabilitiesProxy();
        this.simulated = simulated;
    }

    @Nullable
    RecipeHandlingResult handle(Map.Entry<RecipeCapability<?>, List<Content>> entry) {
        initState();
        this.fillContent(holder, entry);
        this.capability = this.resolveCapability(entry);
        if (this.capability == null) {
            return null;
        } else {
            ContentSlots result = this.handleContents();
            return result == null ? null : new RecipeHandlingResult(this.capability, result);
        }
    }

    private void initState() {
        used = new HashSet<>();
        content = new ContentSlots();
        search = this.simulated ? this.content : new ContentSlots();
    }

    private void fillContent(IRecipeCapabilityHolder holder, Map.Entry<RecipeCapability<?>, List<Content>> entry) {
        RecipeCapability<?> cap = entry.getKey();
        ChanceBoostFunction function = recipe.getType().getChanceFunction();
        ChanceLogic logic = recipe.getChanceLogicForCapability(cap, this.io, this.isTick);
        List<Content> chancedContents = new ArrayList<>();
        for (Content cont : entry.getValue()) {
            if (cont.slotName == null) {
                this.search.content.add(cont.content);
            } else {
                this.search.slots.computeIfAbsent(cont.slotName, s -> new ArrayList<>()).add(cont.content);
            }
            if (simulated) continue;
            if (cont.chance >= cont.maxChance) {
                if (cont.slotName == null) {
                    this.content.content.add(cont.content);
                } else {
                    this.content.slots.computeIfAbsent(cont.slotName, s -> new ArrayList<>()).add(cont.content);
                }
            } else {
                chancedContents.add(cont.copy(cap, ContentModifier.multiplier(1.0 / recipe.parallels)));
            }
        }
        if (!chancedContents.isEmpty()) {
            int recipeTier = RecipeHelper.getPreOCRecipeEuTier(recipe);
            int holderTier = holder.getChanceTier();
            var cache = this.chanceCaches.get(cap);
            chancedContents = logic.roll(chancedContents, function, recipeTier, holderTier, cache, recipe.parallels,
                    cap);
            if (chancedContents == null) return;
            for (Content cont : chancedContents) {
                if (cont.slotName == null) {
                    this.content.content.add(cont.content);
                } else {
                    this.content.slots.computeIfAbsent(cont.slotName, s -> new ArrayList<>()).add(cont.content);
                }
            }
        }
    }

    private RecipeCapability<?> resolveCapability(Map.Entry<RecipeCapability<?>, List<Content>> entry) {
        RecipeCapability<?> capability = entry.getKey();
        if (!capability.doMatchInRecipe()) {
            return null;
        }
        content.content = this.content.content.stream().map(capability::copyContent).toList();
        if (this.content.content.isEmpty() && this.content.slots.isEmpty()) return null;
        if (this.content.content.isEmpty()) content.content = null;
        return capability;
    }

    @Nullable
    private ContentSlots handleContents() {
        this.handleContentsInternal(this.io);
        if (this.content.content == null && this.content.slots.isEmpty()) {
            return null;
        } else {
            this.handleContentsInternal(IO.BOTH);
            return this.content;
        }
    }

    private void handleContentsInternal(IO capIO) {
        if (!capabilityProxies.contains(capIO, capability)) return;

        if (holder instanceof IDistinctMachine iDistinctMachine) {
            ObjectArrayList<IRecipeHandler<?>> handlers;
            if (capIO == IO.IN && (this.capability instanceof ItemRecipeCapability || this.capability instanceof FluidRecipeCapability)) {
                // 隔离
                if (!iDistinctMachine.getRecipeHandleParts().isEmpty() && iDistinctMachine.getDistinctHatch() != null) {
                    handlers = new ObjectArrayList<>(iDistinctMachine.getDistinctHatch().allHandles().get(this.capability));
                } else {
                    handlers = new ObjectArrayList<>(capabilityProxies.get(IO.IN, capability));
                }
            } else {
                handlers = new ObjectArrayList<>(capabilityProxies.get(capIO, capability));
            }
            for (IRecipeHandler<?> handler : handlers) {
                var result = handler.handleRecipe(io, recipe, search.content, null, true);
                if (result == null && content.slots.isEmpty()) {
                    if (!this.simulated) handler.handleRecipe(io, recipe, content.content, null, false);
                    content.content = null;
                    break;
                }
            }
            if (content.content != null || !content.slots.isEmpty()) {
                for (IRecipeHandler<?> proxy : handlers) {
                    if (used.contains(proxy) || proxy.isDistinct()) continue;
                    used.add(proxy);
                    if (content.content != null) {
                        content.content = proxy.handleRecipe(io, recipe, content.content, null, this.simulated);
                    } else if (content.slots.isEmpty()) break;
                }
            }
        }
    }

    public boolean simulatedHandle() {
        if (this.holder instanceof IDistinctMachine iDistinctMachine) {
            List<RecipeHandlePart> recipeHandlingResultList = iDistinctMachine.getRecipeHandleParts();
            List<Object> itemContent = new ObjectArrayList<>();
            List<Object> fluidContent = new ObjectArrayList<>();
            for (Map.Entry<RecipeCapability<?>, List<Content>> entry : this.recipe.inputs.entrySet()) {
                RecipeCapability<?> cap = entry.getKey();
                List<Content> list = entry.getValue();
                for (Content content : list) {
                    if (cap == ItemRecipeCapability.CAP) {
                        itemContent.add(content.content);
                    } else if (cap == FluidRecipeCapability.CAP) {
                        fluidContent.add(content.content);
                    }
                }
            }
            if (itemContent.isEmpty() && fluidContent.isEmpty()) return false;
            boolean foundItem;
            boolean foundFluid;
            for (RecipeHandlePart recipeHandlePart : recipeHandlingResultList) {
                if (itemContent.isEmpty()) {
                    for (var handle : recipeHandlePart.allHandles().get(FluidRecipeCapability.CAP)) {
                        foundFluid = handle.handleRecipe(IO.IN, this.recipe, fluidContent, null, true) == null;
                        if (foundFluid) {
                            iDistinctMachine.setDistinctHatch(recipeHandlePart);
                            return true;
                        }
                    }
                } else if (fluidContent.isEmpty()) {
                    for (var handle : recipeHandlePart.allHandles().get(ItemRecipeCapability.CAP)) {
                        foundItem = handle.handleRecipe(IO.IN, this.recipe, itemContent, null, true) == null;
                        if (foundItem) {
                            iDistinctMachine.setDistinctHatch(recipeHandlePart);
                            return true;
                        }
                    }
                } else {
                    for (var handle : recipeHandlePart.allHandles().get(ItemRecipeCapability.CAP)) {
                        foundItem = handle.handleRecipe(IO.IN, this.recipe, itemContent, null, true) == null;
                        if (foundItem) {
                            for (var h : recipeHandlePart.allHandles().get(FluidRecipeCapability.CAP)) {
                                foundFluid = h.handleRecipe(IO.IN, this.recipe, fluidContent, null, true) == null;
                                if (foundFluid) {
                                    iDistinctMachine.setDistinctHatch(recipeHandlePart);
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    static class ContentSlots {

        public @UnknownNullability List content = new ObjectArrayList<>();
        public @NotNull Map<String, List> slots = new HashMap<>();
    }

    record RecipeHandlingResult(RecipeCapability<?> capability, RecipeRunner.ContentSlots result) {

        public RecipeCapability<?> capability() {
            return this.capability;
        }

        public ContentSlots result() {
            return this.result;
        }
    }

    public record RecipeHandlePart(Object2ObjectOpenHashMap<RecipeCapability<?>, List<IRecipeHandler<?>>> allHandles) {}
}
