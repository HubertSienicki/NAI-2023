package main;

import datamodel.DataModel;
import filereader.Reader;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        try {
            Logger logger = Logger.getLogger(String.valueOf(DataModel.class));
            Reader reader = new Reader("data/perceptron.data");
            List<DataModel> trainingData = reader.readFile();

            for (DataModel model:
                 trainingData) {
                logger.info(String.valueOf(model));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
