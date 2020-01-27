package com.globallogic.reactivestock.generator;

import com.globallogic.reactivestock.properties.InstrumentProperties;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

@Component
public class FxPriceGenerator extends TickGenerator {

    protected FxPriceGenerator(final InstrumentProperties instrumentProperties,
                                  final ThreadPoolTaskScheduler taskScheduler) {
        super(instrumentProperties.getFxUsdPln(), taskScheduler);
    }

    @Override
    protected void onTick() {
        System.out.println("Fx Tick: " + properties);
    }
}
