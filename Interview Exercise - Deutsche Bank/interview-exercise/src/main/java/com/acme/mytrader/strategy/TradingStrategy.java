package com.acme.mytrader.strategy;


import com.acme.mytrader.domain.OrderStrategy;
import com.acme.mytrader.execution.ExecutionService;
import com.acme.mytrader.price.PriceListener;
import com.acme.mytrader.side.Side;
import com.acme.mytrader.strategy.pattern.Context;
import com.acme.mytrader.strategy.pattern.PriceBelowLevel;
import com.acme.mytrader.strategy.pattern.PriceStrategy;
import lombok.extern.slf4j.Slf4j;



/**
 * <pre>
 * User Story: As a trader I want to be able to monitor stock prices such
 * that when they breach a trigger level, orders can be executed automatically
 * </pre>
 */
@Slf4j
public class TradingStrategy implements PriceListener {

    private final OrderStrategy orderStrategy;
    private final Context context; // needed for the Strategy Pattern on price
    private final ExecutionService executionService;  // needed to executed automatically




    /**
     *  //By default, we adopt strategy of PriceBelowLevel()
     *
     * @param orderStrategy
     * @param executionService
     */
    public TradingStrategy(OrderStrategy orderStrategy, ExecutionService executionService){

        this.orderStrategy = orderStrategy;
        this.executionService=executionService;
        this.context = new Context(new PriceBelowLevel()); // strategy object implementation by default is PriceBelowLevel()

    }




    /**
     *
     *  //We can also choose your own Strategy as per the Strategy pattern implementation
     *
     * @param orderStrategy
     * @param executionService
     * @param priceStrategy
     */
    public TradingStrategy(OrderStrategy orderStrategy,ExecutionService executionService,PriceStrategy priceStrategy){
        this.orderStrategy = orderStrategy;
        this.executionService=executionService;
        this.context = new Context(priceStrategy); // if needed - Another  strategy could be adopted at Constructor level
    }





    /**
     *
     *     //price update has to happen in real time, so you should not save in memory for further check
     *     // it has to be as fast as possible as price can vary very quick !
     *     // price update should trigger an event immediately
     *
     *
     * @param security
     * @param price
     */
    @Override
    public void priceUpdate(String security, double price) {

        String stock = orderStrategy.getStock();
        double priceLevel = orderStrategy.getPriceLevel();
        Side side = orderStrategy.getSide();
        int volume = orderStrategy.getVolume();


        if (stock.equals(security)) {

            boolean triggerLevelBreached = context.executeStrategy(price, priceLevel);

            if (triggerLevelBreached) {
                log.info("Stock '"+security+"' => !!! Price Monitor level reached for  alert orderStrategy Id "+orderStrategy.getId()+", StrategyName='"+orderStrategy.getStrategyName()+"'");

                switch(side) {  // good choice as it will throw a compilation if side does not provide the right value
                    case BUY :
                        executionService.buy(stock,price, volume);  // ********   BUY executed automatically
                        break;
                    case SELL :
                        executionService.sell(stock,price, volume); /// ********  SELL executed automatically
                        break;
                }
            }else{
                log.info("Stock '"+security+"'=> Normal price Move for alert orderStrategy Id "+orderStrategy.getId()+", StrategyName='"+orderStrategy.getStrategyName()+"' criteria not met yet");
            }

        }else{
            log.info("Stock price rejected ! You are trying to update the wrong stock price. Stock set by user is '"+orderStrategy.getStock()+"', but you want to update stock name '"+security+"'");
        }

    }



    /**
     *
     * Return the Stock name
     *
     * @return
     */
    public String getStock() {
        return orderStrategy.getStock();
    }


}
