package main;

public class Human {
    private String firstName;
    private String lastName;
    private int age;
    private String passport;

    public Human(String firstName, String lastName, int age, String passport) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.passport = passport;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }
}
