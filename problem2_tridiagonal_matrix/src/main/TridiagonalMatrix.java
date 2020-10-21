package main;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class TridiagonalMatrix {
    private ArrayList<ArrayList<Double>> data;
    private static DecimalFormat df2 = new DecimalFormat("#.###");

    private ArrayList<ArrayList<Double>> generate2dArrayZeros(Integer x_size, Integer y_size) {
        ArrayList<ArrayList<Double>> result = new ArrayList<>();
        for(int i = 0; i < x_size; i++) {
            ArrayList<Double> temp = new ArrayList<>();
            for(int j = 0; j < y_size; j++) {
                temp.add(0.0);
            }
            result.add(temp);
        }
        return result;
    }

    public TridiagonalMatrix(ArrayList<Double> a, ArrayList<Double> b, ArrayList<Double> c, ArrayList<Double> f) {
        data = generate2dArrayZeros(c.size(), c.size() + 1);

        for(int i = 0; i < c.size(); i++) {
            data.get(i).set(i, c.get(i));
            data.get(i).set(c.size(), f.get(i));
            if (i != 0) {
                data.get(i).set(i - 1, a.get(i));
            }
            if (i != c.size() - 1) {
                data.get(i).set(i + 1, b.get(i));
            }
        }
    }

    public ArrayList<ArrayList<Double>> getData() {
        return data;
    }

    private void subtractPreviousLine(Integer index, Double coefficient) {
        for(int i = 0; i < data.get(index).size(); i++) {
            data.get(index).set(i, data.get(index).get(i) - data.get(index - 1).get(i) * coefficient);
        }
    }

    private void subtractNextLine(Integer index, Double coefficient) {
        for(int i = 0; i < data.get(index).size(); i++) {
            data.get(index).set(i, data.get(index).get(i) - data.get(index + 1).get(i) * coefficient);
        }
    }

    public void moveForward(Integer start, Integer count) {
        for(int i = start + 1; i < start + count; i++) {
            subtractPreviousLine(i, data.get(i).get(i - 1) / data.get(i - 1).get(i - 1));
        }
    }

    public void moveBackward(Integer start, Integer count) {
        for(int i = start + count - 2; i >= start; i--) {
            subtractNextLine(i, data.get(i).get(i + 1) / data.get(i + 1).get(i + 1));
        }
    }

    public ArrayList<Double> simpleSolve() {
        ArrayList<Double> result = new ArrayList<>();
        moveForward(0, data.size());
        moveBackward(0, data.size());
        for(int i = 0; i < data.size(); i++) {
            result.add(data.get(i).get(data.size()) / data.get(i).get(i));
        }
        return result;
    }

    public TridiagonalMatrix createSimpleMatrix(int threadCount) {
        int size = data.size() / threadCount;
        ArrayList<Double> a = new ArrayList<>();
        ArrayList<Double> b = new ArrayList<>();
        ArrayList<Double> c = new ArrayList<>();
        ArrayList<Double> f = new ArrayList<>();
        assert size >= 3;
        for(int i = 0; i < threadCount; i++) {
            c.add(data.get(i * size).get(i * size));
            c.add(data.get((i + 1) * size - 1).get((i + 1) * size - 1));
            f.add(data.get(i * size).get(data.size()));
            f.add(data.get((i + 1) * size - 1).get(data.size()));
            if(i != 0) {
                a.add(data.get(i * size).get(i * size - 1));
                a.add(data.get((i + 1) * size - 1).get(i * size - 1));
            } else {
                a.add(0.0);
                a.add(0.0);
            }
            if(i != threadCount - 1) {
                b.add(data.get(i * size).get((i + 1) * size));
                b.add(data.get((i + 1) * size - 1).get((i + 1) * size));
            } else {
                b.add(0.0);
                b.add(0.0);
            }
        }
        return new TridiagonalMatrix(a,b,c,f);
    }

    public Integer getIndex(Integer index, Integer count) {
        if(index % count == 0) {
            return (index / count) * 2;
        } else {
            return (index / count) * 2 + 1;
        }
    }

    public Double calculateRoot(Integer index, Integer count, ArrayList<Double> knownRoots) {
        Double sum = 0.0;
        for(int i = 0; i < data.size(); i++) {
            if(i != index) {
                sum += data.get(index).get(i) * knownRoots.get(getIndex(i, count));
            }
        }
        return (data.get(index).get(data.size()) - sum) / data.get(index).get(index);
    }

    public ArrayList<Double> findRoots(Integer start, Integer count, Integer squareSize, ArrayList<Double> knownRoots) {
        ArrayList<Double> result = new ArrayList<>();
        for(int i = start; i < start + count; i++) {
            Double el = calculateRoot(i, squareSize, knownRoots);
            result.add(el);
        }
        return result;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("\t0.00");
        StringBuilder result = new StringBuilder();
        for (ArrayList<Double> el : data) {
            ArrayList<String> temp = new ArrayList<>();
            Arrays.stream(el.toArray()).forEach(e -> temp.add(df.format(e)));
            result.append(temp.toString()).append("\n");
        };
        return result.toString();
    }
}
