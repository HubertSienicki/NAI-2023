package datamodel;

import java.util.List;

public class Iris {
    private String className;
    private List<Double> data;

    public Iris(String className, List<Double> data) {
        this.className = className;
        this.data = data;
    }

    public String getClassName() {
        return className;
    }

    public List<Double> getData() {
        return data;
    }
}
