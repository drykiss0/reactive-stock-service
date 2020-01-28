package com.globallogic.reactivestock.generator;

import com.globallogic.reactivestock.properties.GeneratorProperties;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

//@Component("USD/PLN")
public class UsdPlnFxPriceGenerator extends TickGenerator {

    protected UsdPlnFxPriceGenerator(final GeneratorProperties generatorProperties,
                                     final ThreadPoolTaskScheduler taskScheduler) {
        super(null, null);
        //super(generatorProperties.getInstruments().get(1), taskScheduler);
    }
}
