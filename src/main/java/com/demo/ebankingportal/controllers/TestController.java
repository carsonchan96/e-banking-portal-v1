package com.demo.ebankingportal.controllers;

import java.util.concurrent.ExecutionException;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// import com.demo.ebankingportal.kafka.JsonKafkaProducer;
// import com.demo.ebankingportal.kafka.KafkaProducer;
import com.demo.ebankingportal.models.User;
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

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    // @Autowired
    // private KafkaProducer kafkaProducer;

    // @Autowired
    // private JsonKafkaProducer jsonKafkaProducer;

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

    // @GetMapping("/kafka/publish")
    // public ResponseEntity<?> publish(@RequestParam("message")String message){
    //     kafkaProducer.sendMessage(message);
    //     return ResponseEntity.ok("Message sent to the topic");
    // }

    // @PostMapping("/publish/user")
    // public ResponseEntity<?> publishUser(@RequestBody User user){
    //     jsonKafkaProducer.sendMessage(user);
    //     return ResponseEntity.ok("Json message send to kafka topic");
    // }
}
