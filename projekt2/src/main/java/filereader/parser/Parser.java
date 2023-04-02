package filereader.parser;

import datamodel.DataModel;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    private Parser() {
    }

    public static DataModel parseFile(String line) {
        String[] values = line.split(",");

        List<Double> features = new ArrayList<>();
        for (int i = 0; i < values.length - 1; i++) {
            features.add(Double.parseDouble(values[i]));
        }

        String className = values[values.length - 1];

        DataModel dataModel = new DataModel(features, className);
        
        return dataModel;
    }
}
