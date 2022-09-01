package com.demo.ebankingportal.controllers;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.ebankingportal.models.Account;
import com.demo.ebankingportal.models.ERole;
import com.demo.ebankingportal.models.Role;
import com.demo.ebankingportal.models.User;
import com.demo.ebankingportal.repositories.AccountRepository;
import com.demo.ebankingportal.repositories.RoleRepository;
import com.demo.ebankingportal.repositories.UserRepository;
import com.demo.ebankingportal.services.DatabaseService;
import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    DatabaseService databaseService;

    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/gendata")
    public void genData() {
        Faker faker = new Faker();
        FakeValuesService fakeValuesService = new FakeValuesService(new Locale("en-US"), new RandomService());
        for (int i = 0; i < 10; i++) {
            String name = faker.name().firstName().toLowerCase();
            String idNo = fakeValuesService.bothify("?-##########").toUpperCase();
            if (!userRepository.existsByUsername(name) && !userRepository.existsByIdentityNo(idNo)) {
                // new user
                User user = new User(name,
                        idNo,
                        name + "@gmail.com",
                        encoder.encode(name + "pwd"));
                
                Set<Role> roles = new HashSet<>();
                Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);
                user.setRoles(roles);
                userRepository.save(user);

                for (int j = 0; j < 10; j++) {
                    // new account for that user
                    String acNo = fakeValuesService.bothify("??##-####-####-####-####-#").toUpperCase();
                    if (!accountRepository.existsByAccountNo(acNo)) {
                        Account account = new Account(
                                user,
                                acNo,
                                "USD");
                        accountRepository.save(account);
                    }
                }
            }
        }
    }

    @GetMapping("/get/accounts")
    public void getAccount(){
        databaseService.genTransaction(100);
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('RM') or hasRole('ADMIN')")
    public String userAccess() {
        return "User Content.";
    }

    @GetMapping("/rm")
    @PreAuthorize("hasRole('RM')")
    public String rmAccess() {
        return "Relation Manager Board.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin Board.";
    }
}
