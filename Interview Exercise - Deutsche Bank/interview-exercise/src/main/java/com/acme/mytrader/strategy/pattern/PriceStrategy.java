package com.acme.mytrader.strategy.pattern;

import org.springframework.stereotype.Component;

@Component
public interface PriceStrategy {

    boolean runOperation(double price, double privelevel);


}
