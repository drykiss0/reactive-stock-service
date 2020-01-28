package com.globallogic.reactivestock.generator;

import com.globallogic.reactivestock.properties.GeneratorProperties;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

//@Component("GOOGL")
public class GoogleStockPriceGenerator extends TickGenerator {

    protected GoogleStockPriceGenerator(final GeneratorProperties generatorProperties,
                                        final ThreadPoolTaskScheduler taskScheduler) {
        super(null, null);
        //super(generatorProperties.getInstruments().get(1), taskScheduler);
    }
}
