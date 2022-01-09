package com.acme.mytrader.strategy;

import com.acme.mytrader.domain.OrderStrategy;
import com.acme.mytrader.execution.ExecutionManager;
import com.acme.mytrader.price.PriceListener;
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



//Unit tests should be written properly – using patterns like (Arrange, Act and Assert/Verify) to cover
//all possible edges or corners
//Test class should focus on one thing - must test one Class Under Test (CUT) at a time



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


        assertNotNull(orderStrategy);
        assertEquals("James08012022",orderStrategy.getStrategyName());
        assertEquals(153, orderStrategy.getPriceLevel(),1e-15);
        assertEquals("CAD",orderStrategy.getStock());
        assertEquals(55,orderStrategy.getVolume());
        assertEquals(Side.BUY,orderStrategy.getSide());
        assertNotNull(orderStrategy.getId());

    }




    @Test
    public void TestTradingStrategyGetStock() {

        OrderStrategy orderStrategy = OrderStrategy.builder()
                .strategyName("Oliver buys low")
                .priceLevel(11)
                .stock("AHD")
                .side(Side.SELL)
                .volume(30)
                .id(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE)
                .build();

        TradingStrategy tradingStrategy= new TradingStrategy(orderStrategy, new ExecutionManager());
        assertEquals("AHD",tradingStrategy.getStock());

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
    //Then when a new price comes in, we update the Price for all the stock affected
    @Test
    public void TestMockPriceListenerListRunnningOK() {


        List<OrderStrategy> orderStrategyList = new ArrayList<>();
        OrderStrategy orderStrategy;

        orderStrategy = OrderStrategy.builder()
                .strategyName("Mike short from 2")
                .priceLevel(11)
                .stock("CAD")
                .side(Side.SELL)
                .volume(30)
                .id(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE)
                .build();

        orderStrategyList.add(orderStrategy);



        orderStrategy = OrderStrategy.builder()
                .strategyName("Julien Long from 546.3")
                .priceLevel(12)
                .stock("NID")
                .side(Side.BUY)
                .volume(10)
                .id(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE)
                .build();

        orderStrategyList.add(orderStrategy);



        orderStrategy = OrderStrategy.builder()
                .strategyName("David 01.01.2022")
                .priceLevel(13)
                .stock("FIT")
                .side(Side.SELL)
                .volume(55)
                .id(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE)
                .build();

        orderStrategyList.add(orderStrategy);


        PriceListener priceSource = new PriceSourceManager(orderStrategyList);


        priceSource.priceUpdate("CAD",2);


        PriceListener mockPriceListener= mock(PriceSourceManager.class);
        doNothing().when(mockPriceListener).priceUpdate(isA(String.class), isA(Integer.class));
        mockPriceListener.priceUpdate("CAD",2);
        verify( mockPriceListener, times(1)).priceUpdate("CAD", 2);


    }




}



