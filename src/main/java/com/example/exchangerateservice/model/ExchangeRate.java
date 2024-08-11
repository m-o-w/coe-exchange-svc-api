package com.example.exchangerateservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.io.Serializable;

@Data
@Document(collection = "rates")
public class ExchangeRate implements Serializable{

    @Id
    private String id;

    @JsonIgnore
    @Field("event_payload")
    private String eventPayload;

    private String baseCurrency;
    private String targetCurrency;
    private String date;
    private Double exchangeRate;

    // Method to parse eventPayload and set the fields
    public void parseEventPayload() {
        if (eventPayload != null) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(eventPayload);
                this.baseCurrency = rootNode.get("base_currency").asText();
                this.targetCurrency = rootNode.get("target_currency").asText();
                this.date = rootNode.get("date").asText();
                this.exchangeRate = rootNode.get("exchange_rate").asDouble();
            } catch (JsonProcessingException e) {
                e.printStackTrace(); // Handle parsing exception if needed
            }
        }
    }
}
