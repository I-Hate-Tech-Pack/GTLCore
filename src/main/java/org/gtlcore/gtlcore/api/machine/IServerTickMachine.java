package org.gtlcore.gtlcore.api.machine;

/**
 * @author nutant
 */
public interface IServerTickMachine {

    default void runTick() {}

    default boolean keepTick() {
        return false;
    }

    default boolean cancelTick() {
        return false;
    }
}
