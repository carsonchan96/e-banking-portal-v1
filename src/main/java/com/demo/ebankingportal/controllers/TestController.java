package com.demo.ebankingportal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.ebankingportal.payload.responses.MessageResponse;
import com.demo.ebankingportal.repositories.AccountRepository;
import com.demo.ebankingportal.repositories.RoleRepository;
import com.demo.ebankingportal.repositories.UserRepository;
import com.demo.ebankingportal.services.DatabaseService;

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

    @GetMapping("/gen/transaction")
    public void genTransactions(@RequestParam(defaultValue = "1") Integer num){
        databaseService.genTransaction(num);
    }

    @GetMapping("/gen/user")
    public void genUsers(@RequestParam(defaultValue = "1") Integer num){
        databaseService.genUser(num);
    }

    @GetMapping("/gen/account")
    public void genAccounts(@RequestParam(defaultValue = "1") Integer num){
        databaseService.genAccount(num);
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
