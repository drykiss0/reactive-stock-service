package com.globallogic.reactivestock.generator;

import com.globallogic.reactivestock.properties.TickGenerationProperties;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class TickGenerator implements Runnable {

    protected final TickGenerationProperties properties;
    private final ThreadPoolTaskScheduler taskScheduler;

    private final AtomicInteger counter = new AtomicInteger();

    @PostConstruct
    private void onStartup() {
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

    protected abstract void onTick();
}