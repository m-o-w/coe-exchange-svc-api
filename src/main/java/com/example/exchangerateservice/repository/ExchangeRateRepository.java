package com.example.exchangerateservice.repository;

import com.example.exchangerateservice.model.ExchangeRate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExchangeRateRepository extends MongoRepository<ExchangeRate, String> {

    // Query based on base currency within event_payload
    @Query("{ 'event_payload': { $regex: ?0, $options: 'i' } }")
    List<ExchangeRate> findByBaseCurrencyInPayload(String baseCurrency);

    // Query based on base currency and date within event_payload
    @Query("{ $and: [ { 'event_payload': { $regex: ?0, $options: 'i' } }, { 'event_payload': { $regex: ?1, $options: 'i' } } ] }")
    List<ExchangeRate> findByBaseCurrencyAndDateInPayload(String baseCurrency, String date);

    // Query based on base currency and target currency within event_payload
    @Query("{ $and: [ { 'event_payload': { $regex: ?0, $options: 'i' } }, { 'event_payload': { $regex: ?1, $options: 'i' } } ] }")
    List<ExchangeRate> findByBaseCurrencyAndTargetCurrencyInPayload(String baseCurrency, String targetCurrency);

    // Query based on base currency, target currency, and date within event_payload
    @Query("{ $and: [ { 'event_payload': { $regex: ?0, $options: 'i' } }, { 'event_payload': { $regex: ?1, $options: 'i' } }, { 'event_payload': { $regex: ?2, $options: 'i' } } ] }")
    List<ExchangeRate> findByBaseCurrencyAndTargetCurrencyAndDateInPayload(String baseCurrency, String targetCurrency, String date);
}
