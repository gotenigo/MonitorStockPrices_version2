package com.acme.mytrader.price;

import com.acme.mytrader.domain.OrderStrategy;
import com.acme.mytrader.execution.ExecutionManager;
import com.acme.mytrader.execution.ExecutionService;
import com.acme.mytrader.strategy.TradingStrategy;
import java.util.ArrayList;
import java.util.List;

public class PriceSourceManager implements PriceSource, PriceListener{

    private List<PriceListener> tradingStrategyList;



    public PriceSourceManager(List<PriceListener> tradingStrategyList) {
        this.tradingStrategyList = tradingStrategyList;
    }


    /**
     *   void addPriceListener(PriceListener listener)
     *
     * @param listener
     */
    @Override
    public void addPriceListener(PriceListener listener) {

        this.tradingStrategyList.add(listener);

    }


    /**
     *   void removePriceListener(PriceListener listener)
     *
     * @param listener
     */
    @Override
    public void removePriceListener(PriceListener listener) {

        this.tradingStrategyList.remove(listener);

    }



    /**
     *
     * @param security
     * @param price
     */
    public void priceUpdate(String security, double price) {

        for (PriceListener tradingStrategy : this.tradingStrategyList) {
                tradingStrategy.priceUpdate(security, price);
        }
    }





    /****
     *
     *  //Util method
     *   convert  List<OrderStrategy>  TO  List<PriceListener>
     *
     * @param orderStrategyList
     * @return
     */
    public static List<PriceListener>  OrderStrategyListToPriceListenerList(List<OrderStrategy> orderStrategyList, ExecutionService executionService) {

        List<PriceListener> vPriceListener = new ArrayList<>();
        orderStrategyList.forEach(x -> vPriceListener.add(   new TradingStrategy(x,executionService)  )  );
        return vPriceListener;

    }




}
