package com.acme.mytrader;

import com.acme.mytrader.domain.OrderStrategy;
import com.acme.mytrader.price.PriceListener;
import com.acme.mytrader.side.Side;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Slf4j
@SpringBootApplication
public class PricingAlertServiceApp {


    public static void main(String[] args) {
        SpringApplication.run(PricingAlertServiceApp.class, args);



    }



}
