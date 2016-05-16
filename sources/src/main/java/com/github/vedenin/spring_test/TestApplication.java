package com.github.vedenin.spring_test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * Created by vedenin on 15.05.16.
 */
@SpringBootApplication
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(new Object[]{TestApplication.class, TestConfig.class}, args);
    }
}
