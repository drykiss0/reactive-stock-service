package com.globallogic.reactivestock.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@Data
@ConfigurationProperties(prefix = "app.generator")
public class GeneratorProperties {

    private List<InstrumentProperties> instruments;
}
