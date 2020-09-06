package main;

public class Passenger extends Human {
    private int minComfort;

    public Passenger(String firstName, String lastName, int age, String passport, int minComfort) {
        super(firstName, lastName, age, passport);
        this.minComfort = minComfort;
    }

    public int getMinComfort() {
        return minComfort;
    }

    public void setMinComfort(int minComfort) {
        this.minComfort = minComfort;
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "name=" + getFirstName() + " " + getLastName() +
                '}';
    }
}
