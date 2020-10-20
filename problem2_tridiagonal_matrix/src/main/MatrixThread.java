package main;

public class MatrixThread extends Thread {
    private TridiagonalMatrix matrix;
    private Integer start;
    private Integer count;
    private String side;

    public MatrixThread(TridiagonalMatrix matrix, Integer start, Integer count, String side) {
        this.matrix = matrix;
        this.start = start;
        this.count = count;
        this.side = side;
    }

    public void run() {
        if(side.equals("forward")) {
            matrix.moveForward(start, count);
        } else {
            matrix.moveBackward(start, count);
        }
    }
}
