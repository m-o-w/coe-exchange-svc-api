package com.example.exchangerateservice.repository;

import com.example.exchangerateservice.model.ExchangeRate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
public class ExchangeRateRepositoryTest {

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    public void setUp() {
        // Ensure the collection is cleared before each test to prevent data contamination
        mongoTemplate.dropCollection(ExchangeRate.class);
        mongoTemplate.createCollection(ExchangeRate.class);
    }

    @Test
    public void testFindByBaseCurrencyInPayload() {
        // Create and save an ExchangeRate object with the correct eventPayload
        ExchangeRate rate = new ExchangeRate();
        rate.setEventPayload("{\"base_currency\": \"AFN\", \"target_currency\": \"BOB\", \"date\": \"09-08-2024\", \"exchange_rate\": 0.093816453164157}");
        mongoTemplate.save(rate);

        // Query using the repository method
        List<ExchangeRate> rates = exchangeRateRepository.findByBaseCurrencyInPayload("\"base_currency\": \"AFN\"");

        // Parse the eventPayload for each retrieved rate
        rates.forEach(ExchangeRate::parseEventPayload);

        // Assert the expected results
        assertThat(rates).hasSize(1);
        assertThat(rates.get(0).getBaseCurrency()).isEqualTo("AFN");
    }

    @Test
    public void testFindByBaseCurrencyAndDateInPayload() {
        // Create and save an ExchangeRate object with the correct eventPayload
        ExchangeRate rate = new ExchangeRate();
        rate.setEventPayload("{\"base_currency\": \"AFN\", \"target_currency\": \"BOB\", \"date\": \"09-08-2024\", \"exchange_rate\": 0.093816453164157}");
        mongoTemplate.save(rate);

        // Query using the repository method
        List<ExchangeRate> rates = exchangeRateRepository.findByBaseCurrencyAndDateInPayload("\"base_currency\": \"AFN\"", "\"date\": \"09-08-2024\"");

        // Parse the eventPayload for each retrieved rate
        rates.forEach(ExchangeRate::parseEventPayload);

        // Assert the expected results
        assertThat(rates).hasSize(1);
        assertThat(rates.get(0).getDate()).isEqualTo("09-08-2024");
    }

    @Test
    public void testFindByBaseCurrencyAndTargetCurrencyInPayload() {
        // Create and save an ExchangeRate object with the correct eventPayload
        ExchangeRate rate = new ExchangeRate();
        rate.setEventPayload("{\"base_currency\": \"AFN\", \"target_currency\": \"BOB\", \"date\": \"09-08-2024\", \"exchange_rate\": 0.093816453164157}");
        mongoTemplate.save(rate);

        // Query using the repository method
        List<ExchangeRate> rates = exchangeRateRepository.findByBaseCurrencyAndTargetCurrencyInPayload("\"base_currency\": \"AFN\"", "\"target_currency\": \"BOB\"");

        // Parse the eventPayload for each retrieved rate
        rates.forEach(ExchangeRate::parseEventPayload);

        // Assert the expected results
        assertThat(rates).hasSize(1);
        assertThat(rates.get(0).getTargetCurrency()).isEqualTo("BOB");
    }

    @Test
    public void testFindByBaseCurrencyAndTargetCurrencyAndDateInPayload() {
        // Create and save an ExchangeRate object with the correct eventPayload
        ExchangeRate rate = new ExchangeRate();
        rate.setEventPayload("{\"base_currency\": \"AFN\", \"target_currency\": \"BOB\", \"date\": \"09-08-2024\", \"exchange_rate\": 0.093816453164157}");
        mongoTemplate.save(rate);

        // Query using the repository method
        List<ExchangeRate> rates = exchangeRateRepository.findByBaseCurrencyAndTargetCurrencyAndDateInPayload("\"base_currency\": \"AFN\"", "\"target_currency\": \"BOB\"", "\"date\": \"09-08-2024\"");

        // Parse the eventPayload for each retrieved rate
        rates.forEach(ExchangeRate::parseEventPayload);

        // Assert the expected results
        assertThat(rates).hasSize(1);
        assertThat(rates.get(0).getExchangeRate()).isEqualTo(0.093816453164157);
    }
}
