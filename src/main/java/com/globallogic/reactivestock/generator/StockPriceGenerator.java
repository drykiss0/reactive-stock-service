package com.globallogic.reactivestock.generator;

import com.globallogic.reactivestock.properties.InstrumentProperties;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

@Component
public class StockPriceGenerator extends TickGenerator {

    protected StockPriceGenerator(final InstrumentProperties instrumentProperties,
                                  final ThreadPoolTaskScheduler taskScheduler) {
        super(instrumentProperties.getNasdaqGoogle(), taskScheduler);
    }

    @Override
    protected void onTick() {
        System.out.println("Stock Tick: " + properties);
    }
}
