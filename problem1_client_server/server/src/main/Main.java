package main;

import com.google.gson.Gson;

import java.io.*;
import java.net.*;

public class Main {
    public static Car deserializeCar(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Car.class);
    }

    public static void main(String[] args) throws Exception {
        String clientJson;
        String carResponse;
        ServerSocket welcomeSocket = new ServerSocket(6789);
        Gson gson = new Gson();

        while(true) {
            Socket connectionSocket = welcomeSocket.accept();
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
            clientJson = inFromClient.readLine();

            System.out.println(clientJson);
            Car car = deserializeCar(clientJson);
            carResponse = car.beep();
            System.out.println("Car beeps: " + carResponse);
            outToClient.writeBytes(carResponse + "\n");
        }
    }
}
