package main;

public class Luggage {
    private int width;
    private int height;
    private int weight;

    public Luggage(int width, int height, int weight) {
        this.width = width;
        this.height = height;
        this. weight = weight;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Luggage{" +
                "width=" + width +
                ", height=" + height +
                ", weight=" + weight +
                '}';
    }
}
