import com.tw.*;
import com.tw.chooser.SmarterParkingLotChooser;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class SmarterParkerTest {
    private List<Parkable> parkables;
    private Parkable parker;
    private Parkable parkingLot1;
    private Parkable parkingLot2;
    private Car car;


    @Before
    public void setUp() throws Exception {
        parkables = new ArrayList<Parkable>();
        parkingLot1 = new ParkingLot(1);
        parkingLot2 = new ParkingLot(2);
        parkables.add(parkingLot1);
        parkables.add(parkingLot2);
        parker = new CompositeParker(parkables, new SmarterParkingLotChooser());
        car = new Car();
    }

    @Test
    public void should_park_and_unpark_the_car() throws Exception {
        Token token = parker.park(car);
        Car unparkedCar = parker.unpark(token);
        assertThat(unparkedCar, is(car));
    }

    @Test
    public void should_park_the_car_in_the_second_parking_lot() {
        parker.park(car);
        parker.park(car);

        assertThat(parkingLot1.getRemainingCarports(), is(0));
        assertThat(parkingLot2.getRemainingCarports(), is(1));
    }

    @Test
    public void should_park_the_car_in_the_first_parking_lot() {
        parkables = new ArrayList<Parkable>();
        parkingLot1 = new ParkingLot(3);
        parkingLot2 = new ParkingLot(2);
        parkables.add(parkingLot1);
        parkables.add(parkingLot2);
        parker = new CompositeParker(parkables, new SmarterParkingLotChooser());

        parker.park(new Car());
        parker.park(new Car());

        assertThat(parkingLot1.getRemainingCarports(), is(2));
        assertThat(parkingLot2.getRemainingCarports(), is(1));
    }

    @Test
    public void should_park_the_car_in_the_first_parking_lot_if_the_use_rate_of_two_parking_lots_are_the_same() {
        parker.park(car);
        parker.park(car);

        assertThat(parkingLot1.getRemainingCarports(), is(0));
        assertThat(parkingLot2.getRemainingCarports(), is(1));
    }

    @Test
    public void should_return_null_if_all_parking_lots_are_full() throws Exception {
        Token[] tokens = new Token[4];
        for (int i = 0; i < 4; i++) {
            tokens[i] = parker.park(new Car());
        }

        assertNotNull(tokens[2]);
        assertNull(tokens[3]);
    }

    @Test
    public void should_return_null_when_unpark_a_car_with_invalid_token() throws Exception {
        Car unparkedCar = parker.unpark(new Token());

        assertNull(unparkedCar);
    }
}
