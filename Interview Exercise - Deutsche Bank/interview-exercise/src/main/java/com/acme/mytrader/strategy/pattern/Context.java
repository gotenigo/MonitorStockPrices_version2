package com.acme.mytrader.strategy.pattern;

import org.springframework.stereotype.Component;

//Strategy Pattern ::  context object  ( whose behavior varies as per its strategy object implementation
@Component
public class Context {

    private PriceStrategy priceStrategy;



    /**
     *
     *     //Constructor
     *     //Context object feeds on strategy object.
     *
     * @param priceStrategy
     */
    public Context(PriceStrategy priceStrategy){
        this.priceStrategy = priceStrategy;
    }


    // Strategy execution as per the  Strategy interface
    public boolean executeStrategy(double price, double privelevel){
        return priceStrategy.runOperation(price, privelevel);
    }


}
