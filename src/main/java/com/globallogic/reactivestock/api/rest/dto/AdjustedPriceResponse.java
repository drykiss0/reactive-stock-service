package com.globallogic.reactivestock.api.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdjustedPriceResponse {

    private String symbol;
    private BigDecimal adjustedPrice;
}
