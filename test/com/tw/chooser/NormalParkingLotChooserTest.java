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

public class NormalParkingLotChooserTest {
    private ParkingLotChooser parkingLotChooser;
    private List<Parkable> parkables;
    private Parkable parkingLot1;
    private Parkable parkingLot2;

    @Before
    public void setUp() throws Exception {
        parkingLotChooser = new NormalParkingLotChooser();
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

        Parkable parkable = parkingLotChooser.getParkingLot(parkables);

        assertNull(parkable);
    }

    @Test
    public void should_return_the_first_parking_lot_if_both_the_parking_lots_is_not_full() {
        when(parkingLot1.isFull()).thenReturn(false);
        when(parkingLot2.isFull()).thenReturn(false);

        Parkable parkable = parkingLotChooser.getParkingLot(parkables);

        assertThat(parkable, is(parkingLot1));
    }

    @Test
    public void should_return_the_second_parking_lot_if_first_parking_lot_is_full(){
        when(parkingLot1.isFull()).thenReturn(true);
        when(parkingLot2.isFull()).thenReturn(false);

        Parkable parkable = parkingLotChooser.getParkingLot(parkables);

        assertThat(parkable, is(parkingLot2));
    }
}
