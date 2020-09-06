package main;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class PassengerTrain extends Train {
    public PassengerTrain(int id, String name, int power, ArrayList<Worker> crew, ArrayList<Carriage> carriages) {
        super(id, name, power, crew, carriages);
    }

    public PassengerTrain() {
        super();
        int id = getRandomInt();
        String name = String.valueOf(getRandomInt());
        int power = getRandomInt();
        ArrayList<Worker> crew = createWorkers(getRandomInt() + 1);

        ArrayList<Carriage> carriages = createPassengerCarriages(getRandomInt() + 1);
        carriages.add(new RestaurantCarriage(id, name, getRandomInt(),
                getRandomInt(), String.valueOf(id), getRandomInt()));

        setId(id);
        setName(name);
        setPower(power);
        setCrew(crew);
        setCarriages(carriages);
    }

    private ArrayList<Worker> createWorkers(int numWorkers) {
        ArrayList<Worker> crew = new ArrayList<>();
        for(int i = 0; i < numWorkers; i++) {
            crew.add(new Worker(String.valueOf(i), String.valueOf(i), i, String.valueOf(i), "train crew"));
        }
        return crew;
    }

    private ArrayList<Passenger> createPassengers(int numPassengers) {
        ArrayList<Passenger> passengers = new ArrayList<>();
        for(int i = 0; i < numPassengers; i++) {
            passengers.add(new Passenger(String.valueOf(i), String.valueOf(i), i, String.valueOf(i), getRandomInt()));
        }
        return passengers;
    }

    private ArrayList<Luggage> createLuggage(int numLuggage) {
        ArrayList<Luggage> luggage = new ArrayList<>();
        for(int i = 0; i < numLuggage; i++) {
            luggage.add(new Luggage(getRandomInt(), getRandomInt(), getRandomInt()));
        }
        return luggage;
    }

    private ArrayList<Carriage> createPassengerCarriages(int numCarriages) {
        ArrayList<Carriage> carriages = new ArrayList<>();
        for(int i = 0; i < numCarriages; i++) {
            carriages.add(new PassengerCarriage(i, String.valueOf(i), getRandomInt(), getRandomInt(), getRandomInt(),
                    getRandomInt() < RANDOM_NUM / 2 ? "coupe" : "reserved seat", getRandomInt() + 1,
                    getRandomInt() + 1, createPassengers(getRandomInt()), createLuggage(getRandomInt())));
        }
        return carriages;
    }

    private int getRandomInt() {
        return ThreadLocalRandom.current().nextInt(0, RANDOM_NUM + 1);
    }

    public int calculateCountPassengers() {
        int countPassengers = 0;
        for(int i = 0; i < getCarriages().size(); i++) {
            if(getCarriages().get(i) instanceof PassengerCarriage) {
                countPassengers += ((PassengerCarriage) getCarriages().get(i)).getPassengers().size();
            }
        }
        return countPassengers;
    }

    public int calculateCountLuggage() {
        int countLuggage = 0;
        for(int i = 0; i < getCarriages().size(); i++) {
            if(getCarriages().get(i) instanceof PassengerCarriage) {
                countLuggage += ((PassengerCarriage) getCarriages().get(i)).getLuggage().size();
            }
        }
        return countLuggage;
    }

    public ArrayList<Carriage> sortCarriagesByComfort(String type) {
        ArrayList<Carriage> sortedCarriages = getCarriages();
        if(type.equals("asc")) {
            sortedCarriages.sort((o1, o2) -> {
                if (o1 instanceof PassengerCarriage && o2 instanceof PassengerCarriage) {
                    return Integer.compare(((PassengerCarriage) o1).getComfort(), ((PassengerCarriage) o2).getComfort());
                }
                return 0;
            });
        } else {
            sortedCarriages.sort((o1, o2) -> {
                if (o1 instanceof PassengerCarriage && o2 instanceof PassengerCarriage) {
                    return Integer.compare(((PassengerCarriage) o2).getComfort(), ((PassengerCarriage) o1).getComfort());
                }
                return 0;
            });
        }
        return sortedCarriages;
    }

    public ArrayList<Carriage> findCarriagesByPassengerRange(int low, int high) {
        ArrayList<Carriage> filteredCarriages = new ArrayList<>();
        for(int i = 0; i < getCarriages().size(); i++) {
            if(getCarriages().get(i) instanceof PassengerCarriage) {
                if(low <= ((PassengerCarriage) getCarriages().get(i)).getPassengers().size()
                        && ((PassengerCarriage) getCarriages().get(i)).getPassengers().size() <= high) {
                    filteredCarriages.add(getCarriages().get(i));
                }
            }
        }
        return filteredCarriages;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
