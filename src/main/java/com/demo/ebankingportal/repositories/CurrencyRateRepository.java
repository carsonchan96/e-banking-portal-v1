package com.demo.ebankingportal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.demo.ebankingportal.models.CurrencyRate;

public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, Long> {
    Boolean existsByDate(String date);
    
    @Query(value = "SELECT cr From CurrencyRate cr where currency = :currency and date = :date")
    CurrencyRate findByDateAndCurrency(@Param("currency") String currency, @Param("date") String date);
}
