package com.tw;

public interface Parkable {
    Token park(Car car);

    Car unpark(Token token);

    boolean isFull();

    int getRemainingCarports();

    double getUseRatio();

    String getInfo();
}
