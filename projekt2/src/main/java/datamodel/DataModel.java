package datamodel;

import java.util.List;

public class DataModel {
    private final List<Double> data;
    private String className;

    public DataModel(List<Double> data, String className) {
        this.data = data;
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

    public List<Double> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "DataModel{" +
                "data=" + data +
                ", className='" + className + '\'' +
                '}';
    }
}
