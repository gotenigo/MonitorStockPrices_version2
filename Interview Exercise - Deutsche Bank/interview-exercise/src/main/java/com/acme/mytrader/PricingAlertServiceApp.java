package com.acme.mytrader;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;
import java.util.Comparator;


@Slf4j
@SpringBootApplication
public class PricingAlertServiceApp {


    public static void main(String[] args) {
        SpringApplication.run(PricingAlertServiceApp.class, args);

    }


}
