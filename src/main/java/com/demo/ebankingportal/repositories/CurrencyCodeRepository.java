package com.demo.ebankingportal.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.demo.ebankingportal.models.CurrencyCode;

@Repository
public interface CurrencyCodeRepository extends JpaRepository<CurrencyCode, Integer>{
    Optional<CurrencyCode> findByCode(String code);
    Boolean existsByCode(String code);

    @Query(value = "SELECT cc.code from CurrencyCode cc")
    List<String> getAllCurrencyCode();
}

