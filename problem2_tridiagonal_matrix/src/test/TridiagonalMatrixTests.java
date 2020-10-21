package test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import main.*;

import java.util.ArrayList;

public class TridiagonalMatrixTests {
    static ArrayList<Double> a;
    static ArrayList<Double> b;
    static ArrayList<Double> c;
    static ArrayList<Double> f;
    static ArrayList<Double> a1;
    static ArrayList<Double> b1;
    static ArrayList<Double> c1;
    static ArrayList<Double> f1;

    @BeforeAll
    static void setUp() {
        a = new ArrayList<>();
        a.add(0.0);
        a.add(1.0);
        a.add(1.0);
        a.add(1.0);

        b = new ArrayList<>();
        b.add(1.0);
        b.add(-5.0);
        b.add(2.0);
        b.add(0.0);

        c = new ArrayList<>();
        c.add(2.0);
        c.add(10.0);
        c.add(-5.0);
        c.add(4.0);

        f = new ArrayList<>();
        f.add(-5.0);
        f.add(-18.0);
        f.add(-40.0);
        f.add(-27.0);

        a1 = new ArrayList<>();
        b1 = new ArrayList<>();
        c1 = new ArrayList<>();
        f1 = new ArrayList<>();
        for(int i = 0; i < 12; i++) {
            a1.add((i + 1) + 0.0);
            b1.add((i + 2) + 0.0);
            c1.add((i + 3) + 0.0);
            f1.add(3 * (i + 1) + 3 + 0.0);
        }
        f1.set(0, 5.0);
        f1.set(11, 26.0);
    }

    @Test
    public void testTridiagonalMatrix() {
        TridiagonalMatrix matrix = new TridiagonalMatrix(a,b,c,f);
        assertEquals(matrix.getData().toString(), "[[2.0, 1.0, 0.0, 0.0, -5.0], [1.0, 10.0, -5.0, 0.0, -18.0], [0.0, 1.0, -5.0, 2.0, -40.0], [0.0, 0.0, 1.0, 4.0, -27.0]]");
    }

    @Test
    public void testMoveForward() {
        TridiagonalMatrix matrix = new TridiagonalMatrix(a,b,c,f);
        matrix.moveForward(0, 4);
        assertEquals(matrix.getData().get(1).get(0), 0.0);
        assertEquals(matrix.getData().get(2).get(1), 0.0);
        assertEquals(matrix.getData().get(3).get(2), 0.0);

        matrix = new TridiagonalMatrix(a,b,c,f);
        matrix.moveForward(0, 2);
        matrix.moveForward(2, 2);
        assertEquals(matrix.getData().get(1).get(0), 0.0);
        assertEquals(matrix.getData().get(2).get(1), 1.0);
        assertEquals(matrix.getData().get(3).get(2), 0.0);
    }

    @Test
    public void testMoveBackward() {
        TridiagonalMatrix matrix = new TridiagonalMatrix(a,b,c,f);
        matrix.moveBackward(0, 4);
        assertEquals(matrix.getData().get(0).get(1), 0.0);
        assertEquals(matrix.getData().get(1).get(2), 0.0);
        assertEquals(matrix.getData().get(2).get(3), 0.0);

        matrix = new TridiagonalMatrix(a,b,c,f);
        matrix.moveBackward(2, 2);
        matrix.moveBackward(0, 2);
        assertEquals(matrix.getData().get(0).get(1), 0.0);
        assertEquals(matrix.getData().get(1).get(2), -5.0);
        assertEquals(matrix.getData().get(2).get(3), 0.0);
    }

    @Test
    public void testMoveForwardBackward() {
        TridiagonalMatrix matrix = new TridiagonalMatrix(a,b,c,f);
        matrix.moveForward(0, 4);
        matrix.moveBackward(0, 4);
        assertEquals(matrix.getData().get(1).get(0), 0.0);
        assertEquals(matrix.getData().get(2).get(1), 0.0);
        assertEquals(matrix.getData().get(3).get(2), 0.0);
        assertEquals(matrix.getData().get(0).get(1), 0.0);
        assertEquals(matrix.getData().get(1).get(2), 0.0);
        assertEquals(matrix.getData().get(2).get(3), 0.0);
    }

    @Test
    public void testSimpleSolve() {
        TridiagonalMatrix matrix = new TridiagonalMatrix(a,b,c,f);
        ArrayList<Double> result = matrix.simpleSolve();
        double eps = 0.0001;
        assertTrue(Math.abs(result.get(0) + 3.0) < eps);
        assertTrue(Math.abs(result.get(1) - 1.0) < eps);
        assertTrue(Math.abs(result.get(2) - 5.0) < eps);
        assertTrue(Math.abs(result.get(3) + 8.0) < eps);

        matrix = new TridiagonalMatrix(a1,b1,c1,f1);
        result = matrix.simpleSolve();
        for (double res : result) {
            assertTrue(Math.abs(res - 1) < eps);
        }
    }

    @Test
    public void testCreateSimpleMatrix() {
        TridiagonalMatrix matrix = new TridiagonalMatrix(a1,b1,c1,f1);
        matrix.moveForward(0,4);
        matrix.moveForward(4,4);
        matrix.moveForward(8,4);
        matrix.moveBackward(0,4);
        matrix.moveBackward(4,4);
        matrix.moveBackward(8,4);
        int threadCount = 3;
        TridiagonalMatrix simpleMatrix = matrix.createSimpleMatrix(threadCount);
        ArrayList<Double> result = simpleMatrix.simpleSolve();
        double eps = 0.0001;
        for (double res : result) {
            assertTrue(Math.abs(res - 1) < eps);
        }
    }

    @Test
    public void testFindRoots() {
        ArrayList<Double> roots = new ArrayList<>();
        roots.add(1.0);
        roots.add(1.0);
        roots.add(1.0);
        roots.add(1.0);
        TridiagonalMatrix matrix = new TridiagonalMatrix(a1,b1,c1,f1);
        matrix.moveForward(0,4);
        matrix.moveForward(4,4);
        matrix.moveForward(8,4);
        matrix.moveBackward(0,4);
        matrix.moveBackward(4,4);
        matrix.moveBackward(8,4);
        double eps = 0.0001;
        ArrayList<Double> result = matrix.findRoots(0,12, 4,roots);
        for (double res : result) {
            assertTrue(Math.abs(res - 1) < eps);
        }
    }
}