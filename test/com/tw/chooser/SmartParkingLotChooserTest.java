package com.tw.chooser;

import com.tw.Parkable;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SmartParkingLotChooserTest {
    private SmartParkingLotChooser smartParkingLotChooser;
    private List<Parkable> parkables;
    private Parkable parkingLot1;
    private Parkable parkingLot2;

    @Before
    public void setUp() throws Exception {
        smartParkingLotChooser = new SmartParkingLotChooser();
        parkables = new ArrayList<Parkable>();
        parkingLot1 = mock(Parkable.class);
        parkingLot2 = mock(Parkable.class);
        parkables.add(parkingLot1);
        parkables.add(parkingLot2);
    }

    @Test
    public void should_return_null_if_all_the_parking_lots_is_full() {
        when(parkingLot1.isFull()).thenReturn(true);
        when(parkingLot2.isFull()).thenReturn(true);

        Parkable parkable = smartParkingLotChooser.getParkingLot(parkables);

        assertNull(parkable);
    }

    @Test
    public void should_return_the_first_parking_lot_if_remaining_carports_of_both_the_parking_lots_are_same() {
        when(parkingLot1.getRemainingCarports()).thenReturn(1);
        when(parkingLot2.getRemainingCarports()).thenReturn(1);

        Parkable parkable = smartParkingLotChooser.getParkingLot(parkables);

        assertThat(parkable, is(parkingLot1));
    }

    @Test
    public void should_return_the_second_parking_lot_if_remaining_carports_of_first_parking_lot_is_fewer() {
        when(parkingLot1.getRemainingCarports()).thenReturn(1);
        when(parkingLot2.getRemainingCarports()).thenReturn(2);

        Parkable parkable = smartParkingLotChooser.getParkingLot(parkables);

        assertThat(parkable, is(parkingLot2));
    }
}
