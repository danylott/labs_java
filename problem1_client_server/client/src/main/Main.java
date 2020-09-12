package main;

import com.google.gson.Gson;

import java.io.*;
import java.net.*;

public class Main {
    public static void main(String[] argv) throws Exception {
        String carResponse;

        Socket clientSocket = new Socket("localhost", 6789);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        Car car = new Car("honda", "crv", 2000);
        String carJson = car.exportToJson();

        outToServer.writeBytes(carJson + '\n');
        carResponse = inFromServer.readLine();
        System.out.println("car beeped on server: " + carResponse);
        clientSocket.close();
    }
}
