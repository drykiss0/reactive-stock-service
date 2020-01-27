package com.globallogic.reactivestock.properties;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class TickGenerationProperties {

    @NotNull
    private BigDecimal initialPrice;
    private BigDecimal maxPercentageDiff = BigDecimal.valueOf(5);
    private long minTickDelay = 1000;
    private long maxTickDelay = 1000;
}
