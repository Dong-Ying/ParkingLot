import com.tw.Car;
import com.tw.ParkingLot;
import com.tw.Token;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ParkingLotTest {
    private ParkingLot parkingLot;
    private Car car;

    @Before
    public void setUp() throws Exception {
        parkingLot = new ParkingLot(1);
        car = new Car();
    }

    @Test
    public void should_return_true_if_the_parking_lot_is_full() {
        parkingLot.park(car);
        assertTrue(parkingLot.isFull());
    }

    @Test
    public void should_return_false_if_the_parking_lot_is_not_full() {
        assertFalse(parkingLot.isFull());
    }

    @Test
    public void should_park_and_unpark_a_car() {
        Token token = parkingLot.park(car);
        Car parkedCar = parkingLot.unpark(token);
        assertEquals(car, parkedCar);
    }

    @Test
    public void should_return_null_if_unpark_a_non_exist_car() {
        Car parkedCar = parkingLot.unpark(new Token());
        assertNull(parkedCar);
    }

    @Test
    public void should_return_null_if_the_parking_lot_is_full() {
        parkingLot.park(new Car());
        Token token = parkingLot.park(new Car());

        assertNull(token);
    }

    @Test
    public void should_return_1_for_the_remaining_carports() {
        assertEquals(1, parkingLot.getRemainingCarports());
    }

    @Test
    public void should_return_0_for_the_remaining_carports() {
        parkingLot.park(car);
        assertEquals(0, parkingLot.getRemainingCarports());
    }

    @Test
    public void should_return_1_for_the_use_ratio() {
        parkingLot.park(car);
        assertThat(parkingLot.getUseRatio(), is(1.0));
    }

    @Test
    public void should_return_0_for_the_use_ratio() {
        assertThat(parkingLot.getUseRatio(), is(0.0));
    }

    @Test
    public void should_return_0_point_5_for_the_use_ratio() {
        parkingLot = new ParkingLot(2);
        parkingLot.park(car);
        assertThat(parkingLot.getUseRatio(), is(0.5));
    }
}
