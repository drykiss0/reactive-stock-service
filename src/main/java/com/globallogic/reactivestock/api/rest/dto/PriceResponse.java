package com.globallogic.reactivestock.api.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Currency;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceResponse {

    private String symbol;
    private Currency currency;
    private BigDecimal price;
}
