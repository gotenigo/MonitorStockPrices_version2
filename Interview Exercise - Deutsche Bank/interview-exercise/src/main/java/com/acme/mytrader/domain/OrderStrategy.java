package com.acme.mytrader.domain;

import com.acme.mytrader.side.Side;
import lombok.*;


@Value //will create an immutable class
@Builder // will add a Builder pattern to the Person class
//@AllArgsConstructor(access = AccessLevel.PRIVATE) //Hides the constructor to force useage of the Builder.
public final class OrderStrategy {


    private  final long id;
    private  final double priceLevel; // need for user to define price level to take action on
    @NonNull
    private  final String stock;  // needed for user to define which stock price to monitor (security == stock !)
    private  final int volume; // needed for user to define the volume to buy
    @NonNull
    private  final Side side;  // needed for user to define the side to action on
    private  final String strategyName; // user can define a name for each Strategy



    /**
     *
     * @param id
     * @param priceLevel
     * @param stock
     * @param volume
     * @param side
     * @param strategyName
     */
    private OrderStrategy(long id, double priceLevel, String stock, int volume, Side side, String strategyName) {

        if (volume == 0) throw new IllegalArgumentException ("The volume cant be 0 ! Please check your setup");

        this.id = id;
        this.priceLevel = priceLevel;
        this.stock = stock;
        this.volume = volume;
        this.side = side;
        this.strategyName = strategyName;
    }

    //Here, for immutability, cloning the object is not needed as we are returning primitive and String
    public double getPriceLevel() {
        return priceLevel;
    }

    public String getStock() {
        return stock;
    }

    public int getVolume() {
        return volume;
    }

    public long getId() {
        return id;
    }

    public Side getSide() {
        return side;
    }

    public String getStrategyName() {
        return strategyName;
    }



    @Override
    public String toString() {
        return "OrderStrategy{" +
                ", priceLevel=" + priceLevel +
                ", stock='" + stock + '\'' +
                ", volume=" + volume +
                ", side=" + side +
                ", strategyName='" + strategyName + '\'' +
                '}';
    }


}
