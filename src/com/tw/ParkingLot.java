package com.tw;

import java.util.HashMap;
import java.util.Map;

public class ParkingLot implements Parkable {
    private int capacity;
    private Map carList = new HashMap<Token, Car>();

    public ParkingLot(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public Token park(Car car) {
        Token token = null;
        if (!isFull()) {
            token = new Token();
            carList.put(token, car);
        }
        return token;
    }

    @Override
    public Car unpark(Token token) {
        return (Car) carList.remove(token);
    }

    @Override
    public boolean isFull() {
        return carList.size() >= capacity;
    }

    @Override
    public int getRemainingCarports() {
        return capacity - carList.size();
    }

    @Override
    public double getUseRatio() {
        return (double) carList.size() / capacity;
    }

    @Override
    public String getInfo() {
        return "parking lot : " + capacity;
    }
}