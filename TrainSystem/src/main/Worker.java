package main;

public class Worker extends Human {
    private String position;

    public Worker(String firstName, String lastName, int age, String passport, String position) {
        super(firstName, lastName, age, passport);
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "\nWorker{" +
                "firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", age='" + getAge() + '\'' +
                ", passport='" + getPassport() + '\'' +
                ", position='" + getPosition() + '\'' +
                '}';
    }
}
