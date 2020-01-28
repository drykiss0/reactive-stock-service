package com.globallogic.reactivestock.generator;

import com.globallogic.reactivestock.properties.GeneratorProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TickGenerators {

    private final GeneratorProperties properties;
    private final ThreadPoolTaskScheduler taskScheduler;

    private Map<String, TickGenerator> generators;

    @PostConstruct
    private void createGenerators() {

        final Map<String, TickGenerator> mutableGenerators = new HashMap<>();
        properties.getInstruments().forEach(instrument -> {
            mutableGenerators.put(
                    instrument.getSymbol(),
                    new TickGenerator(instrument, taskScheduler));
        });

        this.generators = Collections.unmodifiableMap(mutableGenerators);
    }

    @EventListener
    private void startOnCtxStarted(ContextRefreshedEvent event) {
        this.generators.values().forEach(gen -> {
            taskScheduler.execute(gen);
        });
    }

    public BigDecimal getCurrentPrice(final String symbol) {
        return generators.get(symbol).getPrice();
    }

    public List<String> getSymbols() {
        return generators.keySet().stream().sorted().collect(Collectors.toList());
    }
}