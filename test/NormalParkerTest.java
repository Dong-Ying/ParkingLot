import com.tw.*;
import com.tw.chooser.NormalParkingLotChooser;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class NormalParkerTest {
    private List<Parkable> parkables;
    private Parkable parker;

    @Before
    public void setUp() throws Exception {
        parkables = new ArrayList<Parkable>();
        parkables.add(new ParkingLot(1));
        parker = new CompositeParker(parkables, new NormalParkingLotChooser());
    }

    @Test
    public void should_park_and_unpark_the_car() throws Exception {
        Car car = new Car();
        Token token = parker.park(car);
        Car unparkedCar = parker.unpark(token);
        assertThat(unparkedCar, is(car));
    }

    @Test
    public void should_return_null_if_all_parking_lots_are_full() throws Exception {
        Token token1 = parker.park(new Car());
        Token token2 = parker.park(new Car());

        assertNotNull(token1);
        assertNull(token2);
    }

    @Test
    public void should_return_null_when_unpark_a_car_with_invalid_token() throws Exception {
        Car unparkedCar = parker.unpark(new Token());

        assertNull(unparkedCar);
    }
}
