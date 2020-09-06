package test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import main.*;

public class PassengerTrainTests {
    static final int numPassengers = 10;
    static final int numLuggage = 8;
    static final int numCarriages = 15;
    static PassengerTrain pt;

    @BeforeAll
    static void setUp() {
        ArrayList<Passenger> passengers = new ArrayList<>();
        for(int i = 0; i < numPassengers; i++) {
            passengers.add(new Passenger(String.valueOf(i), String.valueOf(i), i, String.valueOf(i),1));
        }

        ArrayList<Luggage> luggage = new ArrayList<>();
        for(int i = 0; i < numLuggage; i++) {
            luggage.add(new Luggage(1, 1, 1));
        }

        ArrayList<Carriage> carriages = new ArrayList<>();
        for(int i = 0; i < numCarriages; i++) {
            carriages.add(new PassengerCarriage(i, String.valueOf(i),
                    1, 1, i % 4,
                    "coupe", 2,
                    3, passengers, luggage));
        }

        pt = new PassengerTrain(1, "name", 100, new ArrayList<>(), carriages);
    }

    @Test
    void calculateCountPassengers() {
        int count = pt.calculateCountPassengers();
        assertEquals(count, numPassengers * numCarriages);
    }

    @Test
    void calculateCountLuggage() {
        int count = pt.calculateCountLuggage();
        assertEquals(count, numLuggage * numCarriages);
    }

    @Test
    void sortCarriagesByComfort() {
        ArrayList<Carriage> carriagesAsc = pt.sortCarriagesByComfort("asc");
        assertEquals(((PassengerCarriage) carriagesAsc.get(0)).getComfort(), 0);

        ArrayList<Carriage> carriagesDesc = pt.sortCarriagesByComfort("desc");
        assertEquals(((PassengerCarriage) carriagesDesc.get(0)).getComfort(), 3);
    }

    @Test
    void findCarriagesByPassengerRange() {
        ArrayList<Carriage> notFound = pt.findCarriagesByPassengerRange(1, 3);
        ArrayList<Carriage> found = pt.findCarriagesByPassengerRange(6, 11);
        ArrayList<Carriage> alsoNotFound = pt.findCarriagesByPassengerRange(11, 23);
        ArrayList<Carriage> alsoFound = pt.findCarriagesByPassengerRange(10, 10);

        assertEquals(notFound.size(), 0);
        assertEquals(found.size(), numCarriages);
        assertEquals(alsoNotFound.size(), 0);
        assertEquals(alsoFound.size(), numCarriages);
    }
}
