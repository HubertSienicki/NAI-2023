package main;

import datamodel.DataObject;
import model.Model;
import parser.fileparser.FileParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        FileParser fp = new FileParser("data/iris.data");
        List<DataObject> testData = fp.parseFile();

        FileParser fp2 = new FileParser("data/iris.test.data");
        List<DataObject> testDataPoints = fp2.parseFile();

        double counter = 0;
        double hits = 0;

        for (DataObject testDataPoint : testDataPoints) {
            String label = Model.classify(testData, testDataPoint, 2);

            if (Objects.equals(label, testDataPoint.getClassName())) {
                hits += 1;
            }
            counter += 1;

            System.out.println("Data points {" + testDataPoint.getData() + "}, Predicted label: " + label + ", Actual label: {" + testDataPoint.getClassName()+ "} Accuracy: " + hits / counter * 100);
        }

        String command = "";
        Scanner sc = new Scanner(System.in);
        List<Double> features = new ArrayList<>();
        String name = "";

        while(command != ".quit"){
            command = sc.nextLine();
            switch (command){
                case "1" ->{
                    for (int i = 0; i < 4; i++) {
                        System.out.println("Type a feature double");
                        features.add(Double.parseDouble(sc.nextLine()));
                    }
                    System.out.println("Type a class name");
                    name = sc.nextLine();
                    System.out.println("Type the amount of nearest neighbors");
                    int k = Integer.parseInt(sc.nextLine());

                    DataObject testObject = new DataObject(name, features);
                    String label = Model.classify(testData, testObject, k);

                    if (Objects.equals(label, testObject.getClassName())) {
                        hits += 1;
                    }
                    counter += 1;

                    System.out.println("Data points {" + testObject.getData() + "}, Predicted label: " + label + ", Actual label: {" + testObject.getClassName()+ "} Accuracy: " + hits / counter * 100);
                }
            }
        }

    }
}
