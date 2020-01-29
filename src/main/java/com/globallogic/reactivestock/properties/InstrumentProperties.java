package com.globallogic.reactivestock.properties;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Currency;

@Data
public class InstrumentProperties {

    @NotBlank
    private String symbol;
    @NotNull
    private BigDecimal initialPrice;
    @NotBlank
    private Currency currency;
    @Positive
    private BigDecimal maxPercentageDiff = BigDecimal.valueOf(5);
    @Positive
    private long minTickDelay = 1000;
    @Positive
    private long maxTickDelay = 1000;

    public boolean isCurrencyPair() {
        return symbol.contains("/")
                && symbol.toUpperCase().strip().endsWith(currency.toString());
    }
}
