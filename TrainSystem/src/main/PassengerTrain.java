package main;

import java.util.ArrayList;

public class PassengerTrain extends Train {
    public PassengerTrain(int id, String name, int power, ArrayList<Worker> crew, ArrayList<Carriage> carriages) {
        super(id, name, power, crew, carriages);
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
        sortedCarriages.sort((o1, o2) -> {
            if (o1 instanceof PassengerCarriage && o2 instanceof PassengerCarriage) {
                return Integer.compare(((PassengerCarriage) o1).getComfort(), ((PassengerCarriage) o2).getComfort());
            }
            return 0;
        });
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
}
