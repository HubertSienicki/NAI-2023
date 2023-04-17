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
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.in;
import static java.lang.System.out;
import static menu.Menu.startMenu;

public class Main {
    public static void main(String[] args) {
        try {
            Reader reader = new Reader("projekt2/data/perceptron.data");
            List<DataModel> trainingData = reader.readFile();

            Reader reader2 = new Reader("projekt2/data/perceptron.test.data");
            List<DataModel> testData = reader2.readFile();


            Perceptron perceptron = new Perceptron(
                    trainingData.get(0).getData().size(),
                    0.1,
                    "Iris-versicolor",
                    "Iris-virginica",
                    0.5
            );

            perceptron.train(trainingData, 70);

            perceptron.showResults(testData);

            Scanner sc = new Scanner(in);
            List<Double> features = new ArrayList<>();

            out.println("-----------------Menu-----------------");
            out.println("1. Manually classify labels");
            out.println(".quit Quit the application");

            startMenu(perceptron, sc, features);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}

