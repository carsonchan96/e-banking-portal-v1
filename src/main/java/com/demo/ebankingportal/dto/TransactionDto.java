package com.demo.ebankingportal.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.demo.ebankingportal.models.ETranType;

import lombok.Data;

@Data
public class TransactionDto {
    private UUID id;
    private String account;
    private Float amount;
    private String currency;
    private ETranType type;
    private String iban;
    private String description;
    private LocalDateTime exec_date;
}
