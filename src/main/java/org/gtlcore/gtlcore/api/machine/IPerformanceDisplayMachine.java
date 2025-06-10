package org.gtlcore.gtlcore.api.machine;

/**
 * @author nutant
 */
public interface IPerformanceDisplayMachine extends IServerTickMachine {

    int gtlcore$getTickTime();

    void gtlcore$observe();
}
