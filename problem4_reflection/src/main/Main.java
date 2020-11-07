package main;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class Main {

    public static void getClassDescription(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            Constructor<?> ctor = clazz.getConstructor(String.class);
            Object object = ctor.newInstance(className);
            System.out.println("Attributes of class " + className + ": " + Arrays.toString(object.getClass().getDeclaredFields()));
            System.out.println("Methods of class " + className + ": " + Arrays.toString(object.getClass().getDeclaredMethods()));
            Class<?> base = object.getClass().getSuperclass();
            System.out.println("Interfaces of class " + className + ": " + Arrays.toString(object.getClass().getAnnotatedInterfaces()));
            System.out.println("Base class of class " + className + ": " + base.getName() + "\n");
            if(!base.getName().equals("java.lang.Object")) {
                getClassDescription(base.getName());
            }

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
	    getClassDescription("main.A");
    }
}
