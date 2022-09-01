package com.demo.ebankingportal.models;

import java.util.UUID;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "transactions")
public class Transaction extends Base {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "uuid-char")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    @NotNull
    private Account account;

    @Column(name = "type", columnDefinition = "BIT")
    @NotNull
    private ETranType type;

    @Column(name = "amount")
    @NotNull
    private Double amount;

    @Column(name = "currency")
    @NotBlank
    private String currency;

    @Column(name = "iban")
    @NotBlank
    private String iban;

    @Column(name = "description", length = 500)
    private String description;

    public Transaction() {
    }

    public Transaction(Account account, ETranType type, Double amount, String currency, String iban,
            String description) {
        this.account = account;
        this.type = type;
        this.amount = amount;
        this.currency = currency;
        this.iban = iban;
        this.description = description;
    }

    public UUID getId() {
        return id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public ETranType getType() {
        return type;
    }

    public void setType(ETranType type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
