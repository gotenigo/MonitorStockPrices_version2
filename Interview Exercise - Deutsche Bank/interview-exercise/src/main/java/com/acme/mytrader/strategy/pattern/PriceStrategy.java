package com.acme.mytrader.strategy.pattern;

import org.springframework.stereotype.Component;

@Component
public interface PriceStrategy {

    public boolean runOperation(double price, double privelevel );


}
