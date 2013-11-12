package com.tw.chooser;

import com.tw.Parkable;
import com.tw.ParkingLot;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SmarterParkingLotChooserTest {
    private SmarterParkingLotChooser smarterParkingLotChooser;
    private List<Parkable> parkables;
    private Parkable parkingLot1;
    private Parkable parkingLot2;

    @Before
    public void setUp() throws Exception {
        smarterParkingLotChooser = new SmarterParkingLotChooser();
        parkables = new ArrayList<Parkable>();
        parkingLot1 = mock(ParkingLot.class);
        parkingLot2 = mock(ParkingLot.class);
        parkables.add(parkingLot1);
        parkables.add(parkingLot2);
    }

    @Test
    public void should_return_null_if_all_the_parking_lots_is_full() {
        when(parkingLot1.isFull()).thenReturn(true);
        when(parkingLot2.isFull()).thenReturn(true);

        Parkable parkable = smarterParkingLotChooser.getParkingLot(parkables);

        assertNull(parkable);
    }

    @Test
    public void should_return_the_first_parking_lot_if_use_rate_of_both_the_parking_lots_are_same() {
        when(parkingLot1.getUseRatio()).thenReturn(0.5);
        when(parkingLot2.getUseRatio()).thenReturn(0.5);

        Parkable parkable = smarterParkingLotChooser.getParkingLot(parkables);

        assertThat(parkable, is(parkingLot1));
    }

    @Test
    public void should_return_the_second_parking_lot_if_use_rate_of_first_parking_lot_is_higher() {
        when(parkingLot1.getUseRatio()).thenReturn(0.7);
        when(parkingLot2.getUseRatio()).thenReturn(0.5);

        Parkable parkable = smarterParkingLotChooser.getParkingLot(parkables);

        assertThat(parkable, is(parkingLot2));
    }
}
