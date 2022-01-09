package com.acme.mytrader.price;

import com.acme.mytrader.domain.OrderStrategy;
import com.acme.mytrader.execution.ExecutionManager;
import com.acme.mytrader.strategy.TradingStrategy;
import java.util.ArrayList;
import java.util.List;

public class PriceSourceManager implements PriceSource, PriceListener{

    private List<TradingStrategy> tradingStrategyList;



    public PriceSourceManager(List<TradingStrategy> tradingStrategyList) {
        this.tradingStrategyList = tradingStrategyList;
    }


    /**
     *   void addPriceListener(PriceListener listener)
     *
     * @param listener
     */
    @Override
    public void addPriceListener(PriceListener listener) {

        TradingStrategy vTradingStrategy = (TradingStrategy) listener;
        this.tradingStrategyList.add(vTradingStrategy);

    }


    /**
     *   void removePriceListener(PriceListener listener)
     *
     * @param listener
     */
    @Override
    public void removePriceListener(PriceListener listener) {

        TradingStrategy vTradingStrategy = (TradingStrategy) listener;
        this.tradingStrategyList.remove(vTradingStrategy);

    }



    /**
     *
     * @param security
     * @param price
     */
    public void priceUpdate(String security, double price) {

        for (TradingStrategy tradingStrategy : this.tradingStrategyList) {
            String vStock = tradingStrategy.getStock();
            if (vStock.equals(security)) {
                tradingStrategy.priceUpdate(security, price);
            }
        }
    }





    /****
     *
     *  //Util method
     *   convert  List<OrderStrategy>  TO  List<TradingStrategy>
     *
     * @param orderStrategyList
     * @return
     */
    public static List<TradingStrategy>  OrderStrategyListToTradingStrategyList(List<OrderStrategy> orderStrategyList) {

        List<TradingStrategy> vTradingStrategy = new ArrayList<>();
        orderStrategyList.forEach(x -> vTradingStrategy.add(   new TradingStrategy(x,new ExecutionManager())  )  );

        return vTradingStrategy;
    }




}
