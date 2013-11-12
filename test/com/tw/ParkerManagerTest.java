package com.tw;

import com.tw.chooser.NormalParkingLotChooser;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ParkerManagerTest {
    private CompositeParker parkerManager;
    private List<Parkable> parkers;
    private Parkable parker1;
    private List<ParkingLot> parkingLots;
    private ParkingLot parkingLot1;
    private Token token;
    private Car car;

    @Before
    public void setUp() throws Exception {
        token = new Token();
        car = new Car();
        parkerManager = createParkerManagerWithOneParkerAndOneParkingLot();
    }

    @Test
    public void should_delegate_to_a_parker_to_park_and_unpark_a_car() {
        parkerManager = createParkerManagerWithAvailableParker();

        assertThat(parkerManager.park(car), is(token));
        assertThat(parkerManager.unpark(token), is(car));
    }

    @Test
    public void should_park_and_unpark_a_car_by_himself_if_all_parker_can_not_park_the_car() {
        parkerManager = createParkerManagerWithoutAvailableParker();

        assertThat(parkerManager.park(car), is(token));
        assertThat(parkerManager.unpark(token), is(car));
    }

    @Test
    public void should_park_and_unpark_the_car_unsuccesfully() {
        parkerManager = createParkerManagerWithoutAvailableParkerAndWithOutAvailableParkingLots();

        assertThat(parkerManager.park(car), nullValue());
        assertThat(parkerManager.unpark(token), nullValue());
    }

    private CompositeParker createParkerManagerWithOneParkerAndOneParkingLot() {
        NormalParkingLotChooser parkingLotChooser = new NormalParkingLotChooser();
        parker1 = mock(Parker.class);
        parkers = new ArrayList<Parkable>();
        parkers.add(parker1);
        parkingLot1 = mock(ParkingLot.class);
        parkingLots = new ArrayList<ParkingLot>();
        parkingLots.add(parkingLot1);
        List<Parkable> parkables = new ArrayList<Parkable>();
        for (Parkable parker : parkers) {
            parkables.add(parker);
        }
        for (ParkingLot parkingLot : parkingLots) {
            parkables.add(parkingLot);
        }
        return new CompositeParker(parkables);
    }

    private CompositeParker createParkerManagerWithAvailableParker() {
        CompositeParker parkerManager = createParkerManagerWithOneParkerAndOneParkingLot();
        when(parker1.park(car)).thenReturn(token);
        when(parker1.unpark(token)).thenReturn(car);

        return parkerManager;
    }

    private CompositeParker createParkerManagerWithoutAvailableParker() {
        CompositeParker parkerManager = createParkerManagerWithOneParkerAndOneParkingLot();
        when(parker1.park(car)).thenReturn(null);
        when(parker1.unpark(token)).thenReturn(null);
        when(parkingLot1.park(car)).thenReturn(token);
        when(parkingLot1.unpark(token)).thenReturn(car);

        return parkerManager;
    }

    private CompositeParker createParkerManagerWithoutAvailableParkerAndWithOutAvailableParkingLots() {
        CompositeParker parkerManager = createParkerManagerWithOneParkerAndOneParkingLot();
        when(parker1.park(car)).thenReturn(null);
        when(parker1.unpark(token)).thenReturn(null);
        when(parkingLot1.park(car)).thenReturn(null);
        when(parkingLot1.unpark(token)).thenReturn(null);

        return parkerManager;
    }
}
