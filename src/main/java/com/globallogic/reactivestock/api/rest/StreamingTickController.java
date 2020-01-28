package com.globallogic.reactivestock.api.rest;

import com.globallogic.reactivestock.api.rest.dto.AdjustedPriceResponse;
import com.globallogic.reactivestock.streaming.TickStreamingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Validated
@RestController
@RequestMapping("/api/streaming/ticks")
@RequiredArgsConstructor
public class StreamingTickController {

    private final TickStreamingService tickStreamingService;

    @GetMapping(value = "/{symbol}", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<AdjustedPriceResponse> getAdjustedSymbolPrice(@PathVariable("symbol") final String symbol) {
        return tickStreamingService.getAdjustedPriceFlux(symbol, "USD/PLN");
    }
}
