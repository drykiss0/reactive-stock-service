package com.globallogic.reactivestock.properties;

import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
public class InstrumentProperties {

    @NotBlank
    private String symbol;
    @NotNull
    private BigDecimal initialPrice;
    @Positive
    private BigDecimal maxPercentageDiff = BigDecimal.valueOf(5);
    @Positive
    private long minTickDelay = 1000;
    @Positive
    private long maxTickDelay = 1000;
}
