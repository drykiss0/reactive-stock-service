package com.globallogic.reactivestock.config;

import com.globallogic.reactivestock.generator.TickGenerator;
import com.globallogic.reactivestock.properties.GeneratorProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import javax.annotation.PostConstruct;

//@Configuration
@RequiredArgsConstructor
public class GeneratorBeansConfigCopy {

    private final ApplicationContext ctx;

    @PostConstruct
    private void init() {
        AutowireCapableBeanFactory beanFactory = ctx.getAutowireCapableBeanFactory();
        final GeneratorProperties properties = beanFactory.getBean(GeneratorProperties.class);
        final ThreadPoolTaskScheduler taskScheduler = beanFactory.getBean(ThreadPoolTaskScheduler.class);

//        properties.getInstruments().forEach(instr -> {
//            beanFactory.createBean(TickGe)
//                    instr.getSymbol() + "-Generator", new TickGenerator(instr, taskScheduler));
//        });
    }
}