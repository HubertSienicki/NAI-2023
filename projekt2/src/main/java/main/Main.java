/*
  @author Hubert Sienicki
 * Implementation of a perceptron using Rosenblatt's algorithm
 * Trains the model until convergence of maximum number of epochs is reached
 * At each iteration, the algorithm makes a prediction based on the current weights and adjusts the weights based on the error in the prediction.
 * The learning rate determines the step size in the weight update, and the initial weights are set randomly.
 */

package main;

import datamodel.DataModel;
import filereader.Reader;
import perceptron.Perceptron;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        try {
            Logger logger = Logger.getLogger(String.valueOf(DataModel.class));

            Reader reader = new Reader("data/perceptron.data");
            List<DataModel> trainingData = reader.readFile();

            Reader reader2 = new Reader("data/perceptron.test.data");
            List<DataModel> testData = reader2.readFile();

            HashMap<Integer, String> map = new HashMap<>();
            map.put(0, "Iris-versicolor");
            map.put(1, "Iris-virginica");

            double accuracy = 0.0;
            int counter = 0;


            Perceptron perceptron = new Perceptron(trainingData.get(0).getData().size(), 0.0001);
            perceptron.train(trainingData, 500);

            //accuracy calculation
            for (DataModel testModel : testData) {
                if (map.get(perceptron.predict(testModel.getData())).equals(testModel.getClassName())) {
                    accuracy += 1.0;
                }
                counter++;

                logger.info(
                        "\nClassified as: {" + map.get(perceptron.predict(testModel.getData())) +
                                "}\nTrue value: {" + testModel.getClassName() +
                                "}\nAccuracy: " + accuracy / Double.parseDouble(String.valueOf(counter)) * 100 + "%\n"
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
