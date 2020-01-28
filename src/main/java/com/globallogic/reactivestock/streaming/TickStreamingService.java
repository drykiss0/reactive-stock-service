package com.globallogic.reactivestock.streaming;

import com.globallogic.reactivestock.api.rest.dto.AdjustedPriceResponse;
import com.globallogic.reactivestock.api.rest.dto.PriceResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.math.RoundingMode;
import java.time.Duration;

@Service
public class TickStreamingService {

    @Value("${app.url-tick-service}")
    private String urlTickServiceBase;

    private UriComponents urlTick;

    @PostConstruct
    private void init() {
        urlTick = UriComponentsBuilder.fromUriString(urlTickServiceBase).path("/api/ticks/{symbol}").build();
    }

    public Flux<AdjustedPriceResponse> getAdjustedPriceFlux(final String symbol, final String currPairSymbol) {
        final Flux<PriceResponse> symbolFlux = getDistinctPriceFlux(symbol);
        final Flux<PriceResponse> currencyFlux = getDistinctPriceFlux(currPairSymbol);

        return Flux.combineLatest(symbolFlux, currencyFlux,
                (sym, curr) -> {
                    System.out.println("Combining: " + sym + " with " + curr);
                    return new AdjustedPriceResponse(
                            sym.getSymbol(),
                            sym.getPrice().multiply(curr.getPrice()).setScale(4, RoundingMode.HALF_UP));
                });
    }

    private Flux<PriceResponse> getDistinctPriceFlux(final String symbol) {
        final Flux<PriceResponse> priceResponseFlux = Flux.interval(Duration.ofSeconds(1))
                .map(sec -> getTick(symbol))
                .flatMap(p -> p)
                .distinct();

        return priceResponseFlux;
    }

    private Mono<PriceResponse> getTick(final String symbol) {

        return WebClient.create()
                .get().uri(urlTick.expand(symbol).toUri())
                .retrieve()
                .bodyToMono(PriceResponse.class);
    }
}
