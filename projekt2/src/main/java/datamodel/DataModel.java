package datamodel;

import java.util.List;

public class DataModel {
    private List<Double> data;
    private String className;

    public DataModel(List<Double> data, String className) {
        this.data = data;
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
