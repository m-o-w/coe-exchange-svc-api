package com.example.exchangerateservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "rates")
public class ExchangeRate_Backup {

    @Id
    private String id;

    @Field("base_currency")
    private String baseCurrency;

    @Field("target_currency")
    private String targetCurrency;

    @Field("date")
    private String date;

    @Field("exchange_rate")
    private Double exchangeRate;
}
