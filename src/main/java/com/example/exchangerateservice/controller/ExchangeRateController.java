package com.example.exchangerateservice.controller;

import com.example.exchangerateservice.model.ExchangeRate;
import com.example.exchangerateservice.repository.ExchangeRateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.exchangerateservice.service.ExchangeRateService;

import java.util.List;

@RestController
public class ExchangeRateController {

    private static final Logger logger = LoggerFactory.getLogger(ExchangeRateService.class);
    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    @Autowired
    private ExchangeRateService exchangeRateService;

    @GetMapping("/api/exchange/rate")
    public ResponseEntity<ExchangeRate> getRate(
            @RequestParam String baseCurrency,
            @RequestParam String targetCurrency,
            @RequestParam String date) {

        logger.info("INT101_FOREX_API: Request received for {}/{}/{}", baseCurrency, targetCurrency, date);
        // Use the service to get the exchange rate, leveraging Redis cache
        ExchangeRate exchangeRate = exchangeRateService.getExchangeRate(baseCurrency, targetCurrency, date);
        // Return the exchange rate if found, or a 404 Not Found status if not found
        if (exchangeRate != null) {
            logger.info("INT101_FOREX_API: Data returned not NULL");
            exchangeRate.parseEventPayload();
            logger.info("INT101_FOREX_API: Data is: {}", exchangeRate.toString());
            return ResponseEntity.ok(exchangeRate);
        } else {
            logger.info("INT101_FOREX_API: Data retrieved is NULL");
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/api/exchange-rate")
    public List<ExchangeRate> getExchangeRate(
            @RequestParam String baseCurrency,
            @RequestParam(required = false) String targetCurrency,
            @RequestParam(required = false) String date) {

        List<ExchangeRate> rates;

        if (targetCurrency != null && date != null) {
            // Query based on base currency, target currency, and date
            rates = exchangeRateRepository.findByBaseCurrencyAndTargetCurrencyAndDateInPayload(
                    "\"base_currency\": \"" + baseCurrency + "\"",
                    "\"target_currency\": \"" + targetCurrency + "\"",
                    "\"date\": \"" + date + "\""
            );
        } else if (targetCurrency != null) {
            // Query based on base currency and target currency
            rates = exchangeRateRepository.findByBaseCurrencyAndTargetCurrencyInPayload(
                    "\"base_currency\": \"" + baseCurrency + "\"",
                    "\"target_currency\": \"" + targetCurrency + "\""
            );
        } else if (date != null) {
            // Query based on base currency and date
            rates = exchangeRateRepository.findByBaseCurrencyAndDateInPayload(
                    "\"base_currency\": \"" + baseCurrency + "\"",
                    "\"date\": \"" + date + "\""
            );
        } else {
            // Query based on base currency only
            rates = exchangeRateRepository.findByBaseCurrencyInPayload("\"base_currency\": \"" + baseCurrency + "\"");
        }

        // Parse the eventPayload to populate the fields
        rates.forEach(ExchangeRate::parseEventPayload);

        return rates;
    }
}
