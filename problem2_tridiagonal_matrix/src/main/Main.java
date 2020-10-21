package main;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        ArrayList<Double> a = new ArrayList<>();
        ArrayList<Double> b = new ArrayList<>();
        ArrayList<Double> c = new ArrayList<>();
        ArrayList<Double> f = new ArrayList<>();
        for(int i = 0; i < 12; i++) {
            a.add(Math.random() * 10);
            b.add(Math.random() * 10);
            c.add(a.get(i) + b.get(i) + Math.random() * 5);
            f.add(Math.random() * 7);
        }
        TridiagonalMatrix matrix = new TridiagonalMatrix(a,b,c,f);

        System.out.println("Initial Matrix:");
        System.out.println(matrix);
        MatrixThread thread1 = new MatrixThread(matrix, 0 ,4, "forward");
        MatrixThread thread2 = new MatrixThread(matrix, 4 ,4, "forward");
        MatrixThread thread3 = new MatrixThread(matrix, 8 ,4, "forward");
        thread1.start();
        thread2.start();
        thread3.start();
        thread1.join();
        thread2.join();
        thread3.join();

        MatrixThread thread1b = new MatrixThread(matrix, 0 ,4, "backward");
        MatrixThread thread2b = new MatrixThread(matrix, 4 ,4, "backward");
        MatrixThread thread3b = new MatrixThread(matrix, 8 ,4, "backward");
        thread1b.start();
        thread2b.start();
        thread3b.start();
        thread1b.join();
        thread2b.join();
        thread3b.join();

        System.out.println("Matrix after moving forward/backward:");
        System.out.println(matrix);
        TridiagonalMatrix simpleMatrix = matrix.createSimpleMatrix(3);
        ArrayList<Double> roots = simpleMatrix.simpleSolve();
        System.out.println("Roots on the edges:");
        System.out.println(roots);
        System.out.println("Final roots:");
        System.out.println(matrix.findRoots(0, 12, 4, roots));

        TridiagonalMatrix matrix1 = new TridiagonalMatrix(a,b,c,f);
        System.out.println(matrix1);
        System.out.println(matrix1.simpleSolve());
    }
}
