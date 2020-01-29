package com.globallogic.reactivestock.clients.tickservice;

import com.globallogic.reactivestock.api.rest.dto.PriceResponse;
import com.globallogic.reactivestock.api.rest.dto.SymbolDescription;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DefaultTickServiceClient implements TickServiceClient {

    @Value("${app.tick-service-base-url}")
    private String tickServiceBaseUrl;

    private final RestTemplate restTemplate;
    private WebClient webClient;


    @PostConstruct
    private void init() {
        webClient = WebClient.create(tickServiceBaseUrl);
    }

    @Override
    public List<SymbolDescription> getSymbols() {

        return restTemplate.exchange(
                UriComponentsBuilder.fromUriString(tickServiceBaseUrl).path("/ticks").build().toUri(),
                HttpMethod.GET, null, new ParameterizedTypeReference<List<SymbolDescription>>() {})
                .getBody();
    }

    @Override
    public Flux<PriceResponse> getDistinctPolledPrices(String symbol) {
        return getDistinctPolledPrices(symbol, Duration.ofSeconds(1));
    }

    @Override
    public Flux<PriceResponse> getDistinctPolledPrices(final String symbol, final Duration pollingDelay) {
        final Flux<PriceResponse> priceResponseFlux = Flux.interval(pollingDelay)
                .map(sec -> getTick(symbol))
                .flatMap(p -> p)
                .distinct();

        return priceResponseFlux;
    }

    @Override
    public Mono<PriceResponse> getTick(final String symbol) {

        return webClient
                .get().uri("/ticks/{symbol}", symbol)
                .retrieve()
                .bodyToMono(PriceResponse.class);
    }
}
