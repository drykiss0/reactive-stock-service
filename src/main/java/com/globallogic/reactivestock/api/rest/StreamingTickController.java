package com.globallogic.reactivestock.api.rest;

import com.globallogic.reactivestock.api.rest.dto.PriceResponse;
import com.globallogic.reactivestock.streaming.TickStreamingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Validated
@RestController
@RequestMapping("/api/streaming/ticks")
@RequiredArgsConstructor
public class StreamingTickController {

    private final TickStreamingService tickStreamingService;

    @GetMapping(value = "/{symbol}", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<PriceResponse> getExchangedPrices(
            @PathVariable("symbol") final String symbol,
            @Valid @NotBlank @RequestParam("currency") final String currency) {

        return tickStreamingService.getExchangedPrices(symbol, currency);
    }
}
