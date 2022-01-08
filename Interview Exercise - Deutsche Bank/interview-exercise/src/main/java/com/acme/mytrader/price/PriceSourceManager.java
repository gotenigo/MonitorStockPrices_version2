package com.acme.mytrader.price;

import com.acme.mytrader.domain.OrderStrategy;
import com.acme.mytrader.execution.ExecutionManager;
import com.acme.mytrader.strategy.TradingStrategy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PriceSourceManager implements PriceSource, PriceListener{

    private List<PriceListener> PriceListenerList;



    public PriceSourceManager(List<OrderStrategy> orderStrategyList) {

        PriceListenerList = new ArrayList<>();
        List<TradingStrategy> tradingStrategyList =  new ArrayList<>();
        orderStrategyList.forEach(x -> tradingStrategyList.add(   new TradingStrategy(x,new ExecutionManager())  )  );
        PriceListenerList.addAll(tradingStrategyList);

    }


    /****
     *
     * @param listener
     */
    @Override
    public void addPriceListener(PriceListener listener) {

        PriceListenerList.add(listener);

    }


    /**
     *
     * @param listener
     */
    @Override
    public void removePriceListener(PriceListener listener) {

        PriceListenerList.remove(listener);

    }



    public void priceUpdate(String security, double price) {

        for (PriceListener priceListener : PriceListenerList) {

            TradingStrategy tradingStrategy = (TradingStrategy) priceListener;
            String vStock = tradingStrategy.getStock();
            if (vStock.equals(security)) {
                tradingStrategy.priceUpdate(security, price);
            }
        }
    }



}
