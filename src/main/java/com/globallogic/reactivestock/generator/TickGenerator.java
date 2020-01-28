package com.globallogic.reactivestock.generator;

import com.globallogic.reactivestock.properties.InstrumentProperties;
import lombok.Getter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

class TickGenerator implements Runnable {

    private static final char UP_ARROW = 0x2191;
    private static final char DOWN_ARROW = 0x2193;

    private final InstrumentProperties properties;
    private final ThreadPoolTaskScheduler taskScheduler;

    @Getter
    private volatile BigDecimal price;

    public TickGenerator(final InstrumentProperties properties, final ThreadPoolTaskScheduler taskScheduler) {
        this.properties = properties;
        this.taskScheduler = taskScheduler;
        this.price = properties.getInitialPrice().setScale(4);
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

        final BigDecimal oldPrice = this.price;
        this.price = BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(rangeMin, rangeMax))
                .setScale(4, RoundingMode.HALF_UP);

        outputTick(oldPrice);
    }

    private void outputTick(final BigDecimal oldPrice) {
        final BigDecimal priceDiffAsPercent = this.price.subtract(oldPrice)
                .multiply(BigDecimal.valueOf(100))
                .divide(oldPrice, RoundingMode.HALF_UP)
                .setScale(2, RoundingMode.HALF_UP);
        final char arrow = priceDiffAsPercent.signum() >= 0 ? UP_ARROW : DOWN_ARROW;

        System.out.println(String.format("%s %s %s%s%%",
                properties.getSymbol(), this.price, arrow, priceDiffAsPercent.abs()));
    }
}