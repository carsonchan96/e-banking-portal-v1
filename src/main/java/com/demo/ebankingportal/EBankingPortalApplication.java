package com.demo.ebankingportal;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class EBankingPortalApplication {

	public static void main(String[] args) {
		SpringApplication.run(EBankingPortalApplication.class, args);
	}

	@PostConstruct
    public void init(){
      // Setting Spring Boot SetTimeZone
      TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
}
