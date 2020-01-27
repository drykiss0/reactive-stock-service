package com.globallogic.reactivestock.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "app.generator")
public class InstrumentProperties {

    private TickGenerationProperties nasdaqGoogle;
    private TickGenerationProperties fxUsdPln;
}
