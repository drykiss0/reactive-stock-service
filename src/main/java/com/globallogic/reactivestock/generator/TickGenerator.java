package com.globallogic.reactivestock.generator;

import com.globallogic.reactivestock.properties.InstrumentProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

@Component
@RequiredArgsConstructor
public class TickGenerator implements Runnable {

    @Getter
    private final InstrumentProperties properties;
    private final ThreadPoolTaskScheduler taskScheduler;

    @Getter
    private volatile BigDecimal price;

    @PostConstruct
    private void onStartup() {
        price = properties.getInitialPrice();
        taskScheduler.execute(this, 1000L);
    }

    @Override
    public void run() {
        onTick();
        final long nextDelay = ThreadLocalRandom.current()
                .nextLong(properties.getMinTickDelay(), properties.getMaxTickDelay() + 1);
        taskScheduler.schedule(
                this,
                new Date(System.currentTimeMillis() + nextDelay));
    }

    protected void onTick() {

        final BigDecimal maxDiff = price.multiply(
                properties.getMaxPercentageDiff().divide(BigDecimal.valueOf(100))
        );

        final double rangeMin = price.subtract(maxDiff).doubleValue();
        final double rangeMax = price.add(maxDiff).doubleValue();

        this.price = BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(rangeMin, rangeMax));

        System.out.println(properties.getSymbol() + " " + this.price);
    };
}