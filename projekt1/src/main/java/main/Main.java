package main;

import datamodel.DataObject;
import model.Model;
import parser.fileparser.FileParser;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        FileParser fp = new FileParser("C:\\Users\\kneiv\\iris.data");
        List<DataObject> testData = fp.parseFile();

        List<Double> testDataPoints = new ArrayList<>();
        testDataPoints.add(3.5);
        testDataPoints.add(3.2);
        testDataPoints.add(12.2);
        testDataPoints.add(6.5);


        String label = Model.classify(testData, new DataObject(null, testDataPoints), 25);

        System.out.println("Predicted label: " + label);
    }
}
