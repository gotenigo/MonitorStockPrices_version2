package com.acme.mytrader.execution;

import org.springframework.stereotype.Component;

@Component
public interface ExecutionService {


    void buy(String security, double price, int volume);

    void sell(String security, double price, int volume);


}
