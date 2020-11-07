package main;

public class A extends Base implements Interface {
    private String name;
    public A(String name) {
        super(name);
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private void print() {
        System.out.println("name: " + this.name);
    }

    @Override
    public void printHello() {
        System.out.println("Hello from A");
    }
}

