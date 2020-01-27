package com.globallogic.reactivestock.generator;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@RequiredArgsConstructor
public abstract class TickGenerator implements Runnable {

    private final ThreadPoolTaskScheduler taskScheduler;
    private final AtomicInteger counter = new AtomicInteger();

    @PostConstruct
    private void onStartup() {
        taskScheduler.execute(this, 1000L);
    }

    @Override
    public void run() {
        System.out.println("Tick");
        onTick();
        taskScheduler.schedule(
                this,
                new Date(System.currentTimeMillis() + 3000));
    }

    protected abstract void onTick();
}