package com.globallogic.reactivestock.api.rest.dto;

import com.globallogic.reactivestock.properties.InstrumentProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.Currency;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SymbolDescription implements Comparable {

    private static final Comparator<SymbolDescription> COMPARATOR = Comparator.comparing(
            SymbolDescription::isCurrencyPair)
            .thenComparing(SymbolDescription::getSymbol)
            .thenComparing(s -> s.getCurrency().toString());

    private String symbol;
    private Currency currency;
    private boolean currencyPair;

    public static SymbolDescription fromProperties(final InstrumentProperties properties) {
        return new SymbolDescription(
                properties.getSymbol(),
                properties.getCurrency(),
                properties.isCurrencyPair()
        );
    }

    @Override
    public int compareTo(Object other) {
        if (!(other instanceof SymbolDescription)) {
            return 1;
        }
        return COMPARATOR.compare(this, (SymbolDescription) other);
    }
}
