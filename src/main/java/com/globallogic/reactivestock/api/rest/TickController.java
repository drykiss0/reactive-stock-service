package com.globallogic.reactivestock.api.rest;

import com.globallogic.reactivestock.api.rest.dto.PriceResponse;
import com.globallogic.reactivestock.generator.TickGenerators;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/ticks")
@RequiredArgsConstructor
public class TickController {

    private final TickGenerators tickGenerators;

    @GetMapping("/{curr1}/{curr2}")
    public PriceResponse getCurrencyPrice(@PathVariable("curr1") final String curr1,
                                          @PathVariable("curr2") final String curr2) {
        return getSymbolPrice(String.format("%s/%s", curr1, curr2));
    }

    @GetMapping("/{symbol}")
    public PriceResponse getSymbolPrice(@PathVariable("symbol") final String symbol) {
        return new PriceResponse(symbol, tickGenerators.getCurrentPrice(symbol));
    }

    @GetMapping
    public List<String> getSymbols() {
        return tickGenerators.getSymbols();
    }
}