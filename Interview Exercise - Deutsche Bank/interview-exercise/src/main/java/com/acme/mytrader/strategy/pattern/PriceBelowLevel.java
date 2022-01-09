package com.acme.mytrader.strategy.pattern;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

// Strategy Pattern :: strategy object
@Slf4j
@Component
public class PriceBelowLevel implements  PriceStrategy {


    /**
     *
     */
    public PriceBelowLevel(){
        log.info("=> Strategy PriceBelowLevel implemented !" );
    }



    @Override
    public boolean runOperation(double price, double privelevel) {

        if ( price < privelevel ){   //as soon as the price of that stock is seen to be below a specified price (e.g. 55.0).

            log.info("new price received ("+price+") is below priveLevel="+privelevel);
            return true; // trigger level breached !

        }else{

            log.info("new price ("+price+") received is above priveLevel="+privelevel);
            return false; // nothing breached, keep cool !
        }

    }


}
