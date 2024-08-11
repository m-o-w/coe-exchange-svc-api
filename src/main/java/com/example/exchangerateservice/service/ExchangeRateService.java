package com.example.exchangerateservice.service;

import com.example.exchangerateservice.model.ExchangeRate;
import com.example.exchangerateservice.repository.ExchangeRateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExchangeRateService {

    private static final Logger logger = LoggerFactory.getLogger(ExchangeRateService.class);

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    @Cacheable(value = "exchangeRates", key = "#baseCurrency + '-' + #targetCurrency + '-' + #date")
    public ExchangeRate getExchangeRate(String baseCurrency, String targetCurrency, String date) {
        String cacheKey = baseCurrency + "-" + targetCurrency + "-" + date;
        logger.info("INT101_FOREX_API: Cache miss. Fetching data from MongoDB for {}/{}/{}", baseCurrency, targetCurrency, date);
        logger.info("INT101_FOREX_API: Redis cache key is: {}", cacheKey);
        List<ExchangeRate> rates = exchangeRateRepository.findByBaseCurrencyAndTargetCurrencyAndDateInPayload(baseCurrency, targetCurrency, date);
        logger.info("INT101_FOREX_API: Executed database repository. Exit service class");
        return rates.isEmpty() ? null : rates.get(0);
    }

}
