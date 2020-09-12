package main;

import com.google.gson.Gson;

public class Car {
    public String name;
    protected String mark;
    private int weight;

    public Car(String name, String mark, int weight) {
        this.name = name;
        this.mark = mark;
        this.weight = weight;
    }

    public String exportToJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
