package main;

import java.util.ArrayList;

public class PassengerTrain extends Train {
    public PassengerTrain(int id, String name, int power, ArrayList<Worker> crew, ArrayList<Carriage> carriages) {
        super(id, name, power, crew, carriages);
    }

    public int calculateCountPassengers() {
        return 0;
    }

    public int calculateCountLuggage() {
        return 0;
    }

    public void sortCarriagesByComfort(String type) {

    }

    public ArrayList<Carriage> findCarriagesBYPassengerRange(int low, int high) {
        return null;
    }
}
