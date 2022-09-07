package com.demo.ebankingportal;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.demo.ebankingportal.controllers.AuthController;

@SpringBootTest
class ControllerTest {

	@Autowired
    private AuthController authController;

    @Test
    public void contextLoads() throws Exception{
        assertThat(authController).isNotNull();
    }
}
