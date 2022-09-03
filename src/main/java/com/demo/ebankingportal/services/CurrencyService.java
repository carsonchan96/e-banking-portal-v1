package com.demo.ebankingportal.services;

import java.io.IOException;
import java.security.DrbgParameters.Reseed;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.ebankingportal.exceptions.CurrencyError;
import com.demo.ebankingportal.models.CurrencyCode;
import com.demo.ebankingportal.models.CurrencyRate;
import com.demo.ebankingportal.repositories.CurrencyCodeRepository;
import com.demo.ebankingportal.repositories.CurrencyRateRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

@Service
public class CurrencyService {
    private static final Logger logger = LoggerFactory.getLogger(CurrencyService.class);

    @Autowired
    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    CurrencyCodeRepository currencyCodeRepository;

    @Autowired
    CurrencyRateRepository currencyRateRepository;

    @Value("${currency.Layer.Api.Key}")
    private String currencyLayerApiKey;

    public void storeCurrencyList() {
        ObjectMapper mapOject = new ObjectMapper();
        HashMap<String, Object> mapping = callCurrencyLayerApi(
                "https://api.apilayer.com/currency_data/list",
                HttpMethod.GET,
                "");
        HashMap<String, String> currencies = mapOject.convertValue(mapping.get("currencies"), HashMap.class);
        for (Map.Entry<String, String> entry : currencies.entrySet()) {
            if (!currencyCodeRepository.existsByCode(entry.getKey())) {
                CurrencyCode currencyCode = new CurrencyCode(entry.getKey(), entry.getValue());
                currencyCodeRepository.save(currencyCode);
            }
        }
    }

    public void storeCurrencyRateList() {
        ObjectMapper mapOject = new ObjectMapper();
        HashMap<String, Object> mapping = callCurrencyLayerApi(
                "https://api.apilayer.com/currency_data/historical?date=" + java.time.LocalDate.now(),
                HttpMethod.GET,
                "");
        String date = (String) mapping.get("date");
        if (!currencyRateRepository.existsByDate(date)) {
            HashMap<String, Double> rates = mapOject.convertValue(mapping.get("quotes"), HashMap.class);
            for (Map.Entry<String, Double> entry : rates.entrySet()) {
                CurrencyRate currencyRate = new CurrencyRate(entry.getKey(), entry.getValue().floatValue(), date);
                currencyRateRepository.save(currencyRate);
            }
        }
    }

    public Float currencyTransform(String source_currency, String target_currency, Float amount, String date) {
        Float r = 0f;
        
        if ("USD".equals(source_currency)) {
            CurrencyRate currencyRate = currencyRateRepository.findByDateAndCurrency(source_currency + target_currency,
                    date);
            if (currencyRate != null) {
                r = amount * currencyRate.getRate();
            }
        } else if ("USD".equals(target_currency)) {
            CurrencyRate currencyRate = currencyRateRepository.findByDateAndCurrency(target_currency + source_currency,
                    date);
            if (currencyRate != null) {
                r = amount / currencyRate.getRate();
            }
        }else{
            logger.error("Currency Error: {}", "Currency cannot be found");
        }

        return r;
    }

    private HashMap<String, Object> callCurrencyLayerApi(String request_url, HttpMethod httpMethod, String body) {
        String url = request_url;
        HttpHeaders headers = new HttpHeaders();
        headers.set("apikey", currencyLayerApiKey);
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, httpMethod, request, String.class);
        HashMap<String, Object> mappingResult = new Gson().fromJson(response.getBody(), HashMap.class);
        return mappingResult;
    }
}
