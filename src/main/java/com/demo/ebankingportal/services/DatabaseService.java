package com.demo.ebankingportal.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.demo.ebankingportal.models.Account;
import com.demo.ebankingportal.models.ERole;
import com.demo.ebankingportal.models.ETranType;
import com.demo.ebankingportal.models.Role;
import com.demo.ebankingportal.models.Transaction;
import com.demo.ebankingportal.models.User;
import com.demo.ebankingportal.repositories.AccountRepository;
import com.demo.ebankingportal.repositories.CurrencyCodeRepository;
import com.demo.ebankingportal.repositories.RoleRepository;
import com.demo.ebankingportal.repositories.TransactionRepository;
import com.demo.ebankingportal.repositories.UserRepository;
import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;

@Service
public class DatabaseService {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    CurrencyCodeRepository currencyCodeRepository;

    @Autowired
    PasswordEncoder encoder;

    public void genTransaction(Integer num) {
        Random rand = new Random();
        Faker faker = new Faker();
        FakeValuesService fakeValuesService = new FakeValuesService(new Locale("en-US"), new RandomService());
        List<Account> accounts = accountRepository.findAll();
        List<ETranType> typeList = Arrays.asList(ETranType.TRANS_IN, ETranType.TRANS_OUT);
        accounts.forEach(account -> {
            Integer i = 0;
            List<Transaction> transactions = new ArrayList<>();
            while (i < num) {
                Transaction transaction = new Transaction(
                        account,
                        typeList.get(rand.nextInt(typeList.size())),
                        (float) faker.number().randomDouble(2, 1000, 500000),
                        account.getCurrencyCode(),
                        fakeValuesService.bothify("??##-####-####-####-####-#").toUpperCase(),
                        faker.company().name());
                transactions.add(transaction);
                i++;
            }
            transactionRepository.saveAll(transactions);
        });
    }

    public void genUser(Integer num) {
        Integer i = 0;
        while (i < num) {
            Faker faker = new Faker();
            FakeValuesService fakeValuesService = new FakeValuesService(new Locale("en-US"), new RandomService());

            String firstname = faker.name().firstName().toLowerCase();
            String lastname = faker.name().lastName().toLowerCase();
            String fullname = firstname + lastname;
            String idNo = fakeValuesService.bothify("?-##########").toUpperCase();
            if (!userRepository.existsByUsername(fullname) && !userRepository.existsByIdentityNo(idNo)) {
                // new user
                User user = new User(fullname,
                        idNo,
                        fullname + "@gmail.com",
                        encoder.encode(fullname + "pwd"));

                Set<Role> roles = new HashSet<>();
                Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);
                user.setRoles(roles);
                userRepository.save(user);
                i++;
            }
        }
    }

    public void genAccount(Integer num) {
        Integer i = 0;
        Random rand = new Random();
        List<User> users = userRepository.getAllUserId();
        List<String> currencies = currencyCodeRepository.getAllCurrencyCode();
        FakeValuesService fakeValuesService = new FakeValuesService(new Locale("en-US"), new RandomService());
        while (i < num) {
            User rand_user = users.get(rand.nextInt(users.size()));
            // new account for that user
            String acNo = fakeValuesService.bothify("??##-####-####-####-####-#").toUpperCase();
            if (!accountRepository.existsByAccountNo(acNo)) {
                Account account = new Account(
                        rand_user,
                        acNo,
                        currencies.get(rand.nextInt(currencies.size())));
                accountRepository.save(account);
                i++;
            }
        }
    }
}
