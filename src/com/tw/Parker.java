package com.tw;

import com.tw.chooser.ParkingLotChooser;

import java.util.List;

public class Parker implements Parkable {
    private List<ParkingLot> parkingLots;
    private ParkingLotChooser parkingLotChooser;

    public Parker(List<ParkingLot> parkingLots, ParkingLotChooser parkingLotChooser) {
        this.parkingLots = parkingLots;
        this.parkingLotChooser = parkingLotChooser;
    }

    public Token park(Car car) {
        Token token = null;
        ParkingLot chosenParkingLot = parkingLotChooser.getParkingLot(parkingLots);
        if (chosenParkingLot != null) {
            token = chosenParkingLot.park(car);
        }
        return token;
    }

    public Car unpark(Token token) {
        Car car = null;
        for (ParkingLot parkingLot : parkingLots) {
            car = parkingLot.unpark(token);
            if (car != null) {
                break;
            }
        }
        return car;
    }
}
