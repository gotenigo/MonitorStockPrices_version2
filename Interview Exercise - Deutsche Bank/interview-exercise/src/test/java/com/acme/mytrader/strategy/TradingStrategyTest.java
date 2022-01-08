package com.acme.mytrader.strategy;

import com.acme.mytrader.domain.OrderStrategy;
import com.acme.mytrader.execution.ExecutionManager;
import com.acme.mytrader.price.PriceListener;
import com.acme.mytrader.price.PriceSourceManager;
import com.acme.mytrader.side.Side;
import org.junit.Test;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TradingStrategyTest {

//Unit tests should be written properly – using patterns like (Arrange, Act and Assert/Verify) to cover
//all possible edges or corners
//Test class should focus on one thing - must test one Class Under Test (CUT) at a time

/*
    The AAA (Arrange-Act-Assert) pattern has become almost a standard across the industry.
    It suggests that you should divide your test method into three sections: arrange, act and assert.
    Each one of them only responsible for the part in which they are named after.
 */

    // >> (1) The Arrange => Create Object  : objects would be created, mocks setup (if you are using one) ? Check object is created ???
    // >> (2) The Act  => Call the Method() :    The invocation of the method being tested.
    // >> (3) The Assert => Check the output : Check whether the expectations were met.
     // ---------- EXAMPLE -
    // arrange
    //var repository = Substitute.For<IClientRepository>();
    //var client = new Client(repository);

    // act
    //client.Save();

    // assert
    //mock.Received.SomeMethod();

    @Test
    public void testNothing() {
    }


    @Test
    public void whenAddCalledVerified() {


        TradingStrategy mockTradingStrategy = mock(TradingStrategy.class);

        //when(mockTradingStrategy.getStock()).thenReturn("CAD");


        OrderStrategy orderStrategy = OrderStrategy.builder()
                .strategyName("James08012022")
                .priceLevel(153)
                .stock("CAD")
                .side(Side.BUY)
                .id(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE)
                .build();

        assertNotNull(orderStrategy);


        assertEquals("James08012022",orderStrategy.getStrategyName());
        assertEquals(153, orderStrategy.getPriceLevel(),1e-15);
        assertEquals("CAD",orderStrategy.getStock());
        assertEquals(Side.BUY,orderStrategy.getSide());
        assertNotNull(orderStrategy.getId());


        //assertSame(expected, actual),
        //assertNotSame(expected, actual)
        //assertArrayEquals(expected, actual)
        //assertEquals(expected[i],actual[i])
        //assertArrayEquals(expected[i],actual[i])


        PriceListener priceSource = new TradingStrategy(orderStrategy, new ExecutionManager() );

        priceSource.priceUpdate("CAD",5);

    }




    @Test
    public void PlayFullData() {



        List<OrderStrategy> orderStrategyList = new ArrayList<>();
        OrderStrategy orderStrategy;

        orderStrategy = OrderStrategy.builder()
                .strategyName("James08012022")
                .priceLevel(153)
                .stock("CAD")
                .side(Side.BUY)
                .id(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE)
                .build();

        orderStrategyList.add(orderStrategy);



        orderStrategy = OrderStrategy.builder()
                .strategyName("James08012022")
                .priceLevel(153)
                .stock("CAD")
                .side(Side.BUY)
                .id(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE)
                .build();

        orderStrategyList.add(orderStrategy);



        orderStrategy = OrderStrategy.builder()
                .strategyName("James08012022")
                .priceLevel(153)
                .stock("CAD")
                .side(Side.BUY)
                .id(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE)
                .build();

        orderStrategyList.add(orderStrategy);




        PriceListener priceSource = new PriceSourceManager(orderStrategyList);


        priceSource.priceUpdate("CAD",454);


    }




}
