package org.gtlcore.gtlcore.utils.datastructure;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

/**
 * Represents a tooltip entry that can be literal text, a translatable language key, or a combination of multiple parts
 *
 * @param text           text content or translation key
 * @param isTranslatable whether this is a translatable key
 * @param args           translation arguments, only for translatable entries
 * @param parts          child parts for combined mode
 * @param style          optional style to apply (color, formatting, etc.)
 */
@SuppressWarnings("unused")
@OnlyIn(Dist.CLIENT)
public record TooltipEntry(String text, boolean isTranslatable, Object[] args, List<TooltipEntry> parts,
                           Style style) {

    public static TooltipEntry literal(@NotNull String text) {
        return new TooltipEntry(text, false, null, null, null);
    }

    public static TooltipEntry literalWithStyle(@NotNull String text, @NotNull Style style) {
        return new TooltipEntry(text, false, null, null, style);
    }

    public static TooltipEntry literalWithColor(@NotNull String text, @NotNull ChatFormatting color) {
        return new TooltipEntry(text, false, null, null, Style.EMPTY.withColor(color));
    }

    public static TooltipEntry translatable(@NotNull String key) {
        return new TooltipEntry(key, true, null, null, null);
    }

    public static TooltipEntry translatable(@NotNull String key, Object... args) {
        return new TooltipEntry(key, true, args, null, null);
    }

    public static TooltipEntry translatableWithStyle(@NotNull String key, @NotNull Style style) {
        return new TooltipEntry(key, true, null, null, style);
    }

    public static TooltipEntry translatableWithStyle(@NotNull String key, @NotNull Style style, Object... args) {
        return new TooltipEntry(key, true, args, null, style);
    }

    public static TooltipEntry translatableWithColor(@NotNull String key, @NotNull ChatFormatting color) {
        return new TooltipEntry(key, true, null, null, Style.EMPTY.withColor(color));
    }

    public static TooltipEntry translatableWithColor(@NotNull String key, @NotNull ChatFormatting color,
                                                     Object... args) {
        return new TooltipEntry(key, true, args, null, Style.EMPTY.withColor(color));
    }

    public static TooltipEntry combined(TooltipEntry... parts) {
        return new TooltipEntry(null, false, null, Arrays.asList(parts), null);
    }

    public static TooltipEntry combined(List<TooltipEntry> parts) {
        return new TooltipEntry(null, false, null, parts, null);
    }

    public Component toComponent() {
        if (parts != null && !parts.isEmpty()) {
            MutableComponent result = Component.empty();
            for (TooltipEntry part : parts) {
                result = result.append(part.toComponent());
            }
            return result;
        }

        MutableComponent component = isTranslatable ? args != null && args.length > 0 ? Component.translatable(text, args) : Component.translatable(text) : Component.literal(text);

        if (style != null) {
            component = component.withStyle(style);
        }

        return component;
    }
}
