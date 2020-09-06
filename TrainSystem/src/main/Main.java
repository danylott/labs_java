package main;

import java.util.Scanner;

public class Main {

    private static void printMenu() {
        System.out.println("\nThings you could do (choose 1-5 or exit please):");
        System.out.println("1 - create Random Train");
        System.out.println("2 - calculate count of Passengers");
        System.out.println("3 - calculate count of Luggage");
        System.out.println("4 - sort Carriages by comfort");
        System.out.println("5 - find Carriages by Passenger count range");
        System.out.println("any other - exit");
    }

    public static void main(String[] args) {
        String operation;
        String choice;
        PassengerTrain pt = null;
        Scanner scanner = new Scanner(System. in);
        while(true) {
            printMenu();
            operation = scanner.next();
            switch (operation) {
                case "1":
                    pt = new PassengerTrain();
                    System.out.println("Created Passenger Train with next parameters:");
                    System.out.println(pt);
                    break;

                case "2":
                    if(pt == null) {
                        System.out.println("Please, create Train at first");
                        break;
                    }
                    System.out.println("Count of Passengers = " + pt.calculateCountPassengers());
                    break;

                case "3":
                    if(pt == null) {
                        System.out.println("Please, create Train at first");
                        break;
                    }
                    System.out.println("Count of Luggage = " + pt.calculateCountLuggage());
                    break;

                case "4":
                    if(pt == null) {
                        System.out.println("Please, create Train at first");
                        break;
                    }
                    System.out.println("choose sort order please: 1 - asc, 2 - desc");
                    choice = scanner.next();
                    switch (choice) {
                        case "1" -> {
                            System.out.println("Asc order of Carriages by comfort:");
                            System.out.println(pt.sortCarriagesByComfort("asc"));
                        }
                        case "2" -> {
                            System.out.println("Desc order of Carriages by comfort:");
                            System.out.println(pt.sortCarriagesByComfort("desc"));
                        }
                        default -> System.out.println("Wrong choice!");
                    }
                    break;

                case "5":
                    if(pt == null) {
                        System.out.println("Please, create Train at first");
                        break;
                    }
                    System.out.println("Please select bottom of count passengers range:");
                    choice = scanner.next();
                    int low, high;
                    try {
                        low = Integer.parseInt(choice);
                    } catch (Exception e) {
                        System.out.println("Error - please provide only numbers!");
                        break;
                    }

                    System.out.println("Please select top of count passengers range:");
                    choice = scanner.next();
                    try {
                        high = Integer.parseInt(choice);
                    } catch (Exception e) {
                        System.out.println("Error - please provide only numbers!");
                        break;
                    }
                    if(low > high) {
                        System.out.println("Error - low is grater than high!");
                        break;
                    }

                    System.out.println("Found Carriages by Passengers count range:");
                    System.out.println(pt.findCarriagesByPassengerRange(low, high));

                    break;

                default:
                    return;

            }
        }
    }
}
