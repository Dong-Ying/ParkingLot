package com.tw;

import com.google.common.base.Joiner;
import com.tw.chooser.ParkingLotChooser;

import java.util.ArrayList;
import java.util.List;

public class CompositeParker implements Parkable {
    private List<Parkable> parkables;
    private ParkingLotChooser parkingLotChooser;

    public CompositeParker(List<Parkable> parkables, ParkingLotChooser parkingLotChooser) {
        this.parkables = parkables;
        this.parkingLotChooser = parkingLotChooser;
    }

    public Token park(Car car) {
        Token token = null;
        Parkable chosenParkingLot = parkingLotChooser.getParkingLot(parkables);
        if (chosenParkingLot != null) {
            token = chosenParkingLot.park(car);
        }
        return token;
    }


    public Car unpark(Token token) {
        Car car = null;
        for (Parkable parkable : parkables) {
            car = parkable.unpark(token);
            if (car != null) {
                break;
            }
        }
        return car;
    }

    @Override
    public boolean isFull() {
        boolean isFull = true;
        for (Parkable parkable : parkables) {
            isFull = isFull && parkable.isFull();
        }
        return isFull;
    }

    @Override
    public int getRemainingCarports() {
        return 0;
    }

    @Override
    public double getUseRatio() {
        return 0;
    }

    @Override
    public String getInfo() {
        String info = "composite parker" + "\n";
        for (Parkable parkable : parkables) {
            String[] parkableInfo = parkable.getInfo().split("\n");
            ArrayList<String> temp = new ArrayList<String>();
            for (String s : parkableInfo) {
                temp.add("--" + s);
            }
            String result = Joiner.on("\n").join(temp) + "\n";
            info += result;
        }
        return info;
    }
}
