package main;

import datamodel.DataObject;
import model.Model;
import parser.fileparser.FileParser;

import java.util.List;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        FileParser fp = new FileParser("C:\\Users\\kneiv\\iris.data");
        List<DataObject> testData = fp.parseFile();

        FileParser fp2 = new FileParser("C:\\Users\\kneiv\\iris.test.data");
        List<DataObject> testDataPoints = fp2.parseFile();

        int counter = 0;
        int hits = 0;

        for (DataObject testDataPoint : testDataPoints) {
            String label = Model.classify(testData, testDataPoint, 3);

            if (Objects.equals(label, testDataPoint.getClassName())) {
                hits += 1;
            }
            counter += 1;

            System.out.println("Data points {" + testDataPoint.getData() + "}, Predicted label: " + label + ", Accuracy: " + hits / counter * 100);
        }


    }
}
