package com.tw.chooser;

import com.tw.Parkable;

import java.util.List;

public class SmartParkingLotChooser implements ParkingLotChooser {
    @Override
    public Parkable getParkingLot(List<Parkable> parkables) {
        Parkable chosenParkingLot = parkables.get(0);
        for (Parkable parkable : parkables) {
            if (!parkable.isFull() && parkable.getRemainingCarports() > chosenParkingLot.getRemainingCarports()) {
                chosenParkingLot = parkable;
            }
        }
        if(chosenParkingLot.isFull()){
            chosenParkingLot = null;
        }
        return chosenParkingLot;
    }
}
