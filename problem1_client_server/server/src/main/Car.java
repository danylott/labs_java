package main;

public class Car {
    public String name;
    protected String mark;
    private int weight;

    public Car(String name, String mark, int weight) {
        this.name = name;
        this.mark = mark;
        this.weight = weight;
    }

    public String beep() {
       return "The car " + name + " with mark " + mark + " beeped with all it weight " + weight;
    }
}
