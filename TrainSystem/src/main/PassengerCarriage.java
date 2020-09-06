package main;

import java.util.ArrayList;

public class PassengerCarriage extends Carriage {
    private int comfort;
    private String type;
    private int maxPassengers;
    private int maxLuggage;
    private ArrayList<Passenger> passengers;
    private ArrayList<Luggage> luggage;

    public PassengerCarriage(int id, String name, int weight, int height,
                             int comfort, String type, int maxPassengers, int maxLuggage,
                             ArrayList<Passenger> passengers, ArrayList<Luggage> luggage) {
        super(id, name, weight, height);
        this.comfort = comfort;
        this.type = type;
        this.maxPassengers = maxPassengers;
        this.maxLuggage = maxLuggage;
        this.passengers = passengers;
        this.luggage = luggage;
    }

    public void setComfort(int comfort) {
        this.comfort = comfort;
    }

    public int getComfort() {
        return comfort;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setMaxPassengers(int maxPassengers) {
        this.maxPassengers = maxPassengers;
    }

    public int getMaxPassengers() {
        return maxPassengers;
    }

    public int getMaxLuggage() {
        return maxLuggage;
    }

    public void setMaxLuggage(int maxLuggage) {
        this.maxLuggage = maxLuggage;
    }

    public ArrayList<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(ArrayList<Passenger> passengers) {
        this.passengers = passengers;
    }

    public ArrayList<Luggage> getLuggage() {
        return luggage;
    }

    public void setLuggage(ArrayList<Luggage> luggage) {
        this.luggage = luggage;
    }
}
