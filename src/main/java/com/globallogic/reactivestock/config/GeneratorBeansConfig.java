package com.globallogic.reactivestock.config;

import com.globallogic.reactivestock.generator.TickGenerator;
import com.globallogic.reactivestock.properties.GeneratorProperties;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;
import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_SINGLETON;

@Configuration
@RequiredArgsConstructor
public class GeneratorBeansConfig implements BeanFactoryPostProcessor {

    @SneakyThrows
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

        final BeanDefinitionRegistry beanDefinitionRegistry = (BeanDefinitionRegistry) beanFactory;
        final BeanDefinition dynamicBean = BeanDefinitionBuilder
                .rootBeanDefinition(TickGenerator.class)
                .setScope(SCOPE_SINGLETON)
                .getBeanDefinition();

        beanDefinitionRegistry.registerBeanDefinition("dynamicBean", dynamicBean);

        final GeneratorProperties properties = beanFactory.getBean(GeneratorProperties.class);
        final ThreadPoolTaskScheduler taskScheduler = beanFactory.getBean(ThreadPoolTaskScheduler.class);

        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource("/application.yml"));

        Object properties2 =yaml.getObject().getProperty("app")
                .get("app.generator");
        properties.getInstruments().forEach(instr -> {
            beanFactory.registerSingleton(
                    instr.getSymbol() + "-Generator", new TickGenerator(instr, taskScheduler));
        });
    }
}