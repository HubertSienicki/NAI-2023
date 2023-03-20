package datamodel;

import java.util.List;

public class DataObject {
    private String className;
    private List<Double> data;

    public DataObject(String className, List<Double> data) {
        this.className = className;
        this.data = data;
    }

    public String getClassName() {
        return className;
    }

    public List<Double> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Iris{" +
                "className='" + className + '\'' +
                ", data=" + data +
                '}';
    }
}
