package com.demo.ebankingportal.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.ebankingportal.models.Account;
import com.demo.ebankingportal.models.ETranType;
import com.demo.ebankingportal.models.Transaction;
import com.demo.ebankingportal.repositories.AccountRepository;
import com.demo.ebankingportal.repositories.TransactionRepository;
import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;

@Service
public class DatabaseService {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;

    public void genTransaction(Integer num) {
        Random rand = new Random();
        Faker faker = new Faker();
        FakeValuesService fakeValuesService = new FakeValuesService(new Locale("en-US"), new RandomService());
        List<Account> accounts = accountRepository.findAll();
        List<ETranType> typeList = Arrays.asList(ETranType.TRANS_IN, ETranType.TRANS_OUT);
        accounts.forEach(account -> {
            List<Transaction> transactions = new ArrayList<>();
            for (Integer i = 0; i < num; i++) {
                Transaction transaction = new Transaction(
                        account,
                        typeList.get(rand.nextInt(typeList.size())),
                        faker.number().randomDouble(0, 1000, 999999999),
                        faker.currency().code(),
                        fakeValuesService.bothify("??##-####-####-####-####-#").toUpperCase(),
                        faker.company().name());
                // System.out.print(transaction.getCurrency());
                transactions.add(transaction);
            }
            transactionRepository.saveAll(transactions);
        });
    }
}
