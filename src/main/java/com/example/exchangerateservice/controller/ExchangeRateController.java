package com.example.exchangerateservice.controller;

import com.example.exchangerateservice.model.ExchangeRate;
import com.example.exchangerateservice.repository.ExchangeRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ExchangeRateController {

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

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
