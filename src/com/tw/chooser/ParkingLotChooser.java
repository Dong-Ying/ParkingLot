package com.tw.chooser;

import com.tw.Parkable;

import java.util.List;

public interface ParkingLotChooser {
    Parkable getParkingLot(List<Parkable> parkables);
}
