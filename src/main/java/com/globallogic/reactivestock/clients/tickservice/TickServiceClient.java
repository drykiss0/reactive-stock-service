package com.globallogic.reactivestock.clients.tickservice;

import com.globallogic.reactivestock.api.rest.dto.PriceResponse;
import com.globallogic.reactivestock.api.rest.dto.SymbolDescription;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

public interface TickServiceClient {

    List<SymbolDescription> getSymbols();
    Mono<PriceResponse> getTick(String symbol);
    Flux<PriceResponse> getDistinctPolledPrices(String symbol);
    Flux<PriceResponse> getDistinctPolledPrices(String symbol, Duration pollingDelay);
}
