package com.example.exchangerateservice.controller;

import com.example.exchangerateservice.model.ExchangeRate;
import com.example.exchangerateservice.repository.ExchangeRateRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ExchangeRateController.class)
public class ExchangeRateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExchangeRateRepository exchangeRateRepository;

    @Test
    public void testGetExchangeRateByBaseCurrency() throws Exception {
        ExchangeRate rate = new ExchangeRate();
        rate.setBaseCurrency("AFN");
        rate.setTargetCurrency("BOB");
        rate.setDate("09-08-2024");
        rate.setExchangeRate(0.093816453164157);

        Mockito.when(exchangeRateRepository.findByBaseCurrencyInPayload(anyString())).thenReturn(Arrays.asList(rate));

        mockMvc.perform(get("/api/exchange-rate")
                        .param("baseCurrency", "AFN"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].baseCurrency").value("AFN"))
                .andExpect(jsonPath("$[0].targetCurrency").value("BOB"));
    }

    @Test
    public void testGetExchangeRateByBaseCurrencyAndTargetCurrency() throws Exception {
        ExchangeRate rate = new ExchangeRate();
        rate.setBaseCurrency("AFN");
        rate.setTargetCurrency("BOB");
        rate.setDate("09-08-2024");
        rate.setExchangeRate(0.093816453164157);

        Mockito.when(exchangeRateRepository.findByBaseCurrencyAndTargetCurrencyInPayload(anyString(), anyString())).thenReturn(Arrays.asList(rate));

        mockMvc.perform(get("/api/exchange-rate")
                        .param("baseCurrency", "AFN")
                        .param("targetCurrency", "BOB"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].baseCurrency").value("AFN"))
                .andExpect(jsonPath("$[0].targetCurrency").value("BOB"));
    }

    @Test
    public void testGetExchangeRateByBaseCurrencyAndDate() throws Exception {
        ExchangeRate rate = new ExchangeRate();
        rate.setBaseCurrency("AFN");
        rate.setTargetCurrency("BOB");
        rate.setDate("09-08-2024");
        rate.setExchangeRate(0.093816453164157);

        Mockito.when(exchangeRateRepository.findByBaseCurrencyAndDateInPayload(anyString(), anyString())).thenReturn(Arrays.asList(rate));

        mockMvc.perform(get("/api/exchange-rate")
                        .param("baseCurrency", "AFN")
                        .param("date", "09-08-2024"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].date").value("09-08-2024"));
    }

    @Test
    public void testGetExchangeRateByBaseCurrencyTargetCurrencyAndDate() throws Exception {
        ExchangeRate rate = new ExchangeRate();
        rate.setBaseCurrency("AFN");
        rate.setTargetCurrency("BOB");
        rate.setDate("09-08-2024");
        rate.setExchangeRate(0.093816453164157);

        Mockito.when(exchangeRateRepository.findByBaseCurrencyAndTargetCurrencyAndDateInPayload(anyString(), anyString(), anyString())).thenReturn(Arrays.asList(rate));

        mockMvc.perform(get("/api/exchange-rate")
                        .param("baseCurrency", "AFN")
                        .param("targetCurrency", "BOB")
                        .param("date", "09-08-2024"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].exchangeRate").value(0.093816453164157));
    }
}
