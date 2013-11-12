package com.tw.chooser;

import com.tw.Parkable;

import java.util.List;

public class NormalParkingLotChooser implements ParkingLotChooser {
    @Override
    public Parkable getParkingLot(List<Parkable> parkables) {
        Parkable chosenParkable = null;
        for (Parkable parkable : parkables) {
            if (!parkable.isFull()) {
                chosenParkable = parkable;
                break;
            }
        }
        return chosenParkable;
    }
}
