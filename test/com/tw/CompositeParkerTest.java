package com.tw;

import com.tw.chooser.NormalParkingLotChooser;
import com.tw.chooser.ParkingLotChooser;
import com.tw.chooser.SmarterParkingLotChooser;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CompositeParkerTest {
    private CompositeParker parkerBoss;
    private CompositeParker parkerManager;
    private List<Parkable> parkers;
    private Parkable parker1;
    private Parkable parker2;
    private List<Parkable> parkingLots;
    private Parkable parkingLot1;
    private Parkable parkingLot2;
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

    @Test
    public void should_return_true_if_all_parking_lot_is_full(){
        parkerManager = createParkerManagerWithAvailableParker();
        when(parker1.isFull()).thenReturn(true);
        when(parkingLot1.isFull()).thenReturn(true);

        assertTrue(parkerManager.isFull());
    }

    @Test
    public void should_return_false_if_one_parking_lot_is_not_full(){
        parkerManager = createParkerManagerWithAvailableParker();
        when(parker1.isFull()).thenReturn(true);
        when(parkingLot1.isFull()).thenReturn(false);

        assertFalse(parkerManager.isFull());
    }

    @Test
    public void should_print_correct_parkable_info(){
        parkerManager = createParkerManagerWithOneParkerAndOneParkingLotWithoutMock();
        String info = parkerManager.getInfo();
        assertThat(info, is("composite parker\n" +
                "--composite parker\n" +
                "----parking lot : 1\n"+
                "--parking lot : 2\n" +
                "--parking lot : 3\n"));
    }


    private CompositeParker createParkerManagerWithOneParkerAndOneParkingLotWithoutMock() {
        //construct parker
        ArrayList<Parkable> parkingLotsOfParker1 = new ArrayList<Parkable>();
        parkingLotsOfParker1.add(new ParkingLot(1));
        parker1 = new CompositeParker(parkingLotsOfParker1, new SmarterParkingLotChooser());

        List<Parkable> parkables = new ArrayList<Parkable>();
        parkables.add(parker1);
        parkables.add(new ParkingLot(2));
        parkables.add(new ParkingLot(3));
        return new CompositeParker(parkables, new NormalParkingLotChooser());
    }

    private CompositeParker createParkerManagerWithOneParkerAndOneParkingLot() {
        parker1 = mock(Parkable.class);
        parkers = new ArrayList<Parkable>();
        parkers.add(parker1);
        parkingLot1 = mock(ParkingLot.class);
        parkingLots = new ArrayList<Parkable>();
        parkingLots.add(parkingLot1);
        List<Parkable> parkables = new ArrayList<Parkable>();
        for (Parkable parker : parkers) {
            parkables.add(parker);
        }
        for (Parkable parkingLot : parkingLots) {
            parkables.add(parkingLot);
        }
        return new CompositeParker(parkables, new NormalParkingLotChooser());
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
