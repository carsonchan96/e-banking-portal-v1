package com.demo.ebankingportal.services;

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

import com.demo.ebankingportal.models.CurrencyCode;
import com.demo.ebankingportal.repositories.CurrencyCodeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

@Service
public class CurrencyService {

    @Autowired
    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    CurrencyCodeRepository currencyCodeRepository;

    @Value("${currency.Layer.Api.Key}")
    private String currencyLayerApiKey;

    public void storeCurrencyList() {
        ObjectMapper mapOject = new ObjectMapper();
        String url = "https://api.apilayer.com/currency_data/list";
        HttpHeaders headers = new HttpHeaders();
        headers.set("apikey", currencyLayerApiKey);
        HttpEntity<String> request = new HttpEntity<>("", headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        HashMap<String, Object> mapping = new Gson().fromJson(response.getBody(), HashMap.class);
        HashMap<String, String> currencies = mapOject.convertValue(mapping.get("currencies"), HashMap.class);
        for (Map.Entry<String, String> entry : currencies.entrySet()) {
            // System.out.print(entry.getKey()+":"+entry.getValue()+"\n");
            if (!currencyCodeRepository.existsByCode(entry.getKey())) {
                CurrencyCode currencyCode = new CurrencyCode(entry.getKey(), entry.getValue());
                currencyCodeRepository.save(currencyCode);
            }
        }
    }
}
