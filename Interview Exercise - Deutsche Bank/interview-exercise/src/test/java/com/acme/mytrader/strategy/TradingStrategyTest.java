package com.acme.mytrader.strategy;

import com.acme.mytrader.domain.OrderStrategy;
import com.acme.mytrader.execution.ExecutionManager;
import com.acme.mytrader.price.PriceListener;
import com.acme.mytrader.price.PriceSource;
import com.acme.mytrader.price.PriceSourceManager;
import com.acme.mytrader.side.Side;
import com.acme.mytrader.strategy.pattern.PriceStrategy;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TradingStrategyTest {





    @Test
    public void TestOrderStrategyIsCreatedOK() {

        OrderStrategy orderStrategy = OrderStrategy.builder()
                .strategyName("James08012022")
                .priceLevel(153)
                .stock("CAD")
                .side(Side.BUY)
                .volume(55)
                .id(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE)
                .build();


        assertTrue(orderStrategy.toString() instanceof String);

        assertNotNull(orderStrategy);
        assertEquals("James08012022",orderStrategy.getStrategyName());
        assertEquals(153, orderStrategy.getPriceLevel(),1e-15);
        assertEquals("CAD",orderStrategy.getStock());
        assertEquals(55,orderStrategy.getVolume());
        assertEquals(Side.BUY,orderStrategy.getSide());
        assertNotNull(orderStrategy.getId());

    }




    @Test
    public void TestTradingStrategyGetStockAndPriceUpdate() {

        OrderStrategy orderStrategy = OrderStrategy.builder()
                .strategyName("Oliver buys low")
                .priceLevel(11)
                .stock("AHD")
                .side(Side.BUY)
                .volume(30)
                .id(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE)
                .build();

        TradingStrategy tradingStrategy= new TradingStrategy(orderStrategy, new ExecutionManager());

        // We define a function using different rule => We define a PriceStrategy (under Strategy Pattern) via Lambda expression
        PriceStrategy priceAbove = (double price, double priceLevel) -> price>priceLevel;
        // @overwrite  the Strategy Pattern when trading - We construct TradingStrategy object with a new implementation of the Strategy Pattern (price above a rule)
        TradingStrategy tradingStrategy2= new TradingStrategy(orderStrategy, new ExecutionManager(),priceAbove);

        tradingStrategy2.priceUpdate("AHD",40);  // Strategy validated
        tradingStrategy2.priceUpdate("AHD",5);  // Strategy not true
        tradingStrategy2.priceUpdate("XXX",11);  // False price update

        assertTrue(tradingStrategy instanceof TradingStrategy);
        assertTrue(tradingStrategy2 instanceof TradingStrategy);
        assertEquals("AHD",tradingStrategy.getStock());
        assertEquals("AHD",tradingStrategy2.getStock());

    }



    @Test
    public void TestMockTradingStrategypriceUpdate() {

        TradingStrategy mockTradingStrategy = mock(TradingStrategy.class);
        doNothing().when(mockTradingStrategy).priceUpdate(isA(String.class), isA(Integer.class));
        mockTradingStrategy.priceUpdate("FTSE",10_000);
        verify( mockTradingStrategy, times(1)).priceUpdate("FTSE", 10_000);

    }



    @Test(expected = NullPointerException.class)
    public void TestOrderStrategyExceptionThrownWhenWrongArg() {


        OrderStrategy orderStrategy = OrderStrategy.builder()
                .strategyName("James08012022")
                .priceLevel(153)
                .stock(null)
                .side(Side.BUY)
                .volume(55)
                .id(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE)
                .build();
    }




    @Test(expected = IllegalArgumentException.class)
    public void TestOrderStrategyExceptionThrownWhenWrongArg2() {


        OrderStrategy orderStrategy = OrderStrategy.builder()
                .strategyName("James08012022")
                .priceLevel(153)
                .stock("CAD")
                .side(Side.BUY)
                .volume(0)
                .id(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE)
                .build();
    }




    @Test
    public void TestMockPriceCallIsExecuted() {

        PriceListener mockPriceListener = mock(PriceListener.class);
        doNothing().when(mockPriceListener).priceUpdate(isA(String.class), isA(Integer.class));
        mockPriceListener.priceUpdate("CAD",5);
        verify( mockPriceListener, times(1)).priceUpdate("CAD", 5);

    }



    @Test
    public void TestMockPriceCallIsExecutedOthervalue() {

        PriceListener mockPriceListener = mock(PriceListener.class);
        doNothing().when(mockPriceListener).priceUpdate(isA(String.class), isA(Integer.class));
        mockPriceListener.priceUpdate("AHD",15);
        verify( mockPriceListener, times(1)).priceUpdate("AHD", 15);

    }



    @Test
    public void TestMockPriceBelowLevelTrue() {

        PriceStrategy mockPriceStrategy= mock(PriceStrategy.class);
        doReturn(true).when(mockPriceStrategy).runOperation( 5 , 10 );
        boolean ret = mockPriceStrategy.runOperation( 5 , 10 );
        assertThat(ret, is(true));
    }





    @Test
    public void TestMockPriceBelowLevelFalse() {

        PriceStrategy mockPriceStrategy= mock(PriceStrategy.class);
        doReturn(false).when(mockPriceStrategy).runOperation( 15 , 100 );
        boolean ret = mockPriceStrategy.runOperation( 15 , 100 );
        assertThat(ret, is(false));

    }






    //Just to clarify, this test covers some extra work done in PriceSourceManager
    //User can provide a List<OrderStrategy> that will be automatically converted into List<TradingStrategy>
    //Then we can add or Remove new Listener
    //Finally, when a new price comes in, we update the Price for all the stock affected within the List. This is a great feature to have !
    @Test
    public void TestMockPriceListenerListRunningOK() {


        List<OrderStrategy> orderStrategyList = new ArrayList<>();
        OrderStrategy orderStrategy;

        // 1. Add an orderStrategy
        orderStrategy = OrderStrategy.builder()
                .strategyName("Gothard short from 2")
                .priceLevel(11)
                .stock("CAD")
                .side(Side.SELL)
                .volume(30)
                .id(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE)
                .build();

        orderStrategyList.add(orderStrategy );


        // 2. Add an orderStrategy
        orderStrategy = OrderStrategy.builder()
                .strategyName("Julien Long from 546.3")
                .priceLevel(12)
                .stock("NID")
                .side(Side.BUY)
                .volume(10)
                .id(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE)
                .build();

        orderStrategyList.add(orderStrategy );


        // 3. Add an orderStrategy
        orderStrategy = OrderStrategy.builder()
                .strategyName("David 01.01.2022")
                .priceLevel(13)
                .stock("FIT")
                .side(Side.SELL)
                .volume(55)
                .id(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE)
                .build();

        orderStrategyList.add(orderStrategy );


        // 4. Add an orderStrategy
        orderStrategy = OrderStrategy.builder()
                .strategyName("Luke Strategy")
                .priceLevel(5)
                .stock("CAD")
                .side(Side.SELL)
                .volume(5)
                .id(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE)
                .build();

        orderStrategyList.add(orderStrategy );


        List<TradingStrategy> tradingStrategyList = PriceSourceManager.OrderStrategyListToTradingStrategyList(orderStrategyList, new ExecutionManager());
        PriceSourceManager priceSourceManager= new PriceSourceManager(tradingStrategyList);
        PriceListener priceListener = priceSourceManager;
        PriceSource priceSource = priceSourceManager;


        // 5. Add an orderStrategy
        orderStrategy = OrderStrategy.builder()
                .strategyName("Final Strategy")
                .priceLevel(2.52)
                .stock("CAD")
                .side(Side.BUY)
                .volume(23_000)
                .id(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE)
                .build();
        PriceListener newPriceListener = new TradingStrategy(orderStrategy, new ExecutionManager());

        System.out.println("************************ ADD a PriceListener  ******************************");
        priceSource.addPriceListener(newPriceListener); // We add the new PriceListener
        priceListener.priceUpdate("CAD",2); // => Update price
        System.out.println("************************ REMOVE a PriceListener ******************************");
        priceSource.removePriceListener(newPriceListener); // We remove the new PriceListener
        priceListener.priceUpdate("CAD",2); // => Update price

        PriceListener mockPriceListener= mock(PriceSourceManager.class);
        doNothing().when(mockPriceListener).priceUpdate(isA(String.class), isA(Integer.class));
        mockPriceListener.priceUpdate("CAD",2);
        verify( mockPriceListener, times(1)).priceUpdate("CAD", 2);


    }




}



