package main;

import java.util.ArrayList;

public class Train {
    protected static final int RANDOM_NUM = 10;
    private int id;
    private String name;
    private int power;
    private ArrayList<Worker> crew;
    private ArrayList<Carriage> carriages;

    public Train() {

    }

    public Train(int id, String name, int power, ArrayList<Worker> crew, ArrayList<Carriage> carriages) {
        this.id = id;
        this.name = name;
        this.power = power;
        this.crew = crew;
        this.carriages = carriages;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public ArrayList<Worker> getCrew() {
        return crew;
    }

    public void setCrew(ArrayList<Worker> crew) {
        this.crew = crew;
    }

    public ArrayList<Carriage> getCarriages() {
        return carriages;
    }

    public void setCarriages(ArrayList<Carriage> carriages) {
        this.carriages = carriages;
    }

    @Override
    public String toString() {
        return "Train{" +
                "\nid=" + id +
                "\nname='" + name + '\'' +
                "\npower=" + power +
                "\ncrew=" + crew +
                "\ncarriages=" + carriages +
                '}';
    }
}
