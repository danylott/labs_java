package main;

public class RestaurantCarriage extends Carriage {
    private String menu;
    private int countSeats;

    public RestaurantCarriage(int id, String name, int weight, int height, String menu, int countSeats) {
        super(id, name, weight, height);
        this.menu = menu;
        this.countSeats = countSeats;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public int getCountSeats() {
        return countSeats;
    }

    public void setCountSeats(int countSeats) {
        this.countSeats = countSeats;
    }

    @Override
    public String toString() {
        return "\nRestaurantCarriage{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", weight=" + getWeight() +
                ", height=" + getHeight() +
                ", menu='" + menu + '\'' +
                ", countSeats=" + countSeats +
                '}';
    }
}
