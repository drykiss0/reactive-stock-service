package com.globallogic.reactivestock.streaming;

import com.globallogic.reactivestock.api.rest.dto.PriceResponse;
import com.globallogic.reactivestock.api.rest.dto.SymbolDescription;
import com.globallogic.reactivestock.clients.tickservice.TickServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.math.RoundingMode;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TickStreamingService {

    private final TickServiceClient tickServiceClient;

    public Flux<PriceResponse> getExchangedPrices(final String symbol, final String targetCurrency) {

        final Flux<PriceResponse> symbolFlux = tickServiceClient.getDistinctPolledPrices(symbol);
        final Flux<PriceResponse> currencyFlux = tickServiceClient.getDistinctPolledPrices(
                getSymbolForExchangeCurrency(symbol, targetCurrency)
        );

        return Flux.combineLatest(symbolFlux, currencyFlux,
                (sym, curr) -> {
                    System.out.println("Combining: " + sym + " with " + curr);
                    return new PriceResponse(
                            sym.getSymbol(),
                            curr.getCurrency(),
                            sym.getPrice().multiply(curr.getPrice()).setScale(4, RoundingMode.HALF_UP));
                });
    }

    private String getSymbolForExchangeCurrency(String symbol, String targetCurrency) {
        Map<String, SymbolDescription> symbols = tickServiceClient.getSymbols().stream()
                .collect(Collectors.toMap(SymbolDescription::getSymbol, s -> s));

        // TODO validation
        SymbolDescription symbolDescription = symbols.get(symbol);
        return symbols.get(symbolDescription.getCurrency() + "/" + targetCurrency).getSymbol();
    }
}
