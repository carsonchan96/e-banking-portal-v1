package com.demo.ebankingportal.models;

import javax.persistence.*;

@Entity
@Table(name = "currency_codes", uniqueConstraints = {
        @UniqueConstraint(columnNames = "code")
})
public class CurrencyCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column
    private String code;

    @Column
    private String country;

    public CurrencyCode() {
    }

    public CurrencyCode(String code, String country) {
        this.code = code;
        this.country = country;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
