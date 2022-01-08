package com.acme.mytrader.execution;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;



@Slf4j
@Component
public class ExecutionManager implements ExecutionService {

    public ExecutionManager(){}

    @Override
    public void buy(String security, double price, int volume) {
        log.info("==> Buy order executed ! [ security ="+security+" , volume ="+volume+" ] ");
    }

    @Override
    public void sell(String security, double price, int volume) {

        log.info("==> Sell order executed  [ security ="+security+" , volume ="+volume+" ] ! ");
    }
}
