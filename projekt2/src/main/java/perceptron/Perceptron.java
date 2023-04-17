package perceptron;

import datamodel.DataModel;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Perceptron {

    private final double[] weights;
    private final double learningRate;
    private final int numFeatures;
    private final Random random;
    private final HashMap<String, Integer> trainingMap = new HashMap<>();
    private final HashMap<Integer, String> resultMap = new HashMap<>();
    private double accuracy = 0.0;
    private double theta;
    private int counter = 0;

    /**
     * @param numFeatures  specifies a number of input features
     * @param learningRate learning rate of an algorithm
     */
    public Perceptron(int numFeatures, double learningRate, String firstClassName, String secondClassName, double theta) {
        this.numFeatures = numFeatures;
        this.weights = new double[numFeatures];
        this.learningRate = learningRate;
        this.random = new Random();
        this.theta = theta;
        initializeWeights();

        trainingMap.put(firstClassName, 0);
        trainingMap.put(secondClassName, 1);

        resultMap.put(0, firstClassName);
        resultMap.put(1, secondClassName);
    }

    /**
     * Private helper method, sets random values for the weights
     * For each call, generates a random value between 0 and 1
     */
    private void initializeWeights() {
        for (int i = 0; i < weights.length; i++) {
            weights[i] = random.nextDouble();
        }
    }

    /**
     * @param data      list of DataModel objects containing features and class name
     * @param maxEpochs specifies maximum number of epochs to train the perceptron
     */
    public void train(List<DataModel> data, int maxEpochs) {

        //init counter for the number of epochs
        //and a flag whether there were any errors in the current epoch
        int epoch = 0;
        boolean error;

        //training until no errors or maximum number of epochs reached
        do {
            error = false;

            for (DataModel dataPoint : data) {

                //extracting features and labels
                List<Double> features = dataPoint.getData();
                //mapping value to a class name
                int trueClassName = trainingMap.get(dataPoint.getClassName());

                //prediction based on the current weights
                double predictedClassName = predict(features);

                //if the prediction is not correct, flag is set to true and weights are updated
                if (predictedClassName != trueClassName) {
                    error = true;
                    updateWeights(features, trueClassName, predictedClassName);
                }
            }

            epoch++;
        } while (error && epoch < maxEpochs);
    }

    /**
     * Computes dot product of the weights to make the prediction
     *
     * @param features array of doubles representing input features
     * @return If dot product is >= 0, return 0, else return 1
     */
    public int predict(List<Double> features) {
        double net = 0.0;

        for (int i = 0; i < weights.length; i++) {
            net += weights[i] * features.get(i);
        }
        net -= theta;
        return (net >= 0.0) ? 1 : 0;
    }

    /**
     * Private helper to adjust weights based on the error in the prediction
     * @param features       array of doubles representing input features
     * @param label          an integer that represents the true label for the data point.
     * @param predictedLabel a double that represents the predicted label for the data point.
     */
    private void updateWeights(List<Double> features, int label, double predictedLabel) {

        //computes error difference as the difference between true label and predicted label
        double weightCoefficient = label - predictedLabel;

        //for each weight, method updates it by adding the product of learning rate, error and feature value
        for (int i = 0; i < weights.length; i++) {
            weights[i] += learningRate * weightCoefficient * features.get(i);
        }

        theta = theta - learningRate*weightCoefficient;
    }

    public void showResults(List<DataModel> testData) {

        //accuracy calculation
        for (DataModel testModel : testData) {
            if (resultMap.get(this.predict(testModel.getData())).equals(testModel.getClassName())) {
                accuracy += 1.0;
            }
            counter++;

            System.out.println("\nClassified as: {" + resultMap.get(this.predict(testModel.getData())) + "}\nTrue value: {" + testModel.getClassName() + "}\nAccuracy: " + accuracy / Double.parseDouble(String.valueOf(counter)) * 100 + "%\n");
        }
    }

    public void showResults(List<Double> features, String className) {

        if (resultMap.get(this.predict(features)).equals(className)) {
            accuracy += 1.0;
        }
        counter++;

        System.out.println("\nClassified as: {" + resultMap.get(this.predict(features)) + "}\nTrue value: {" + className + "}\nAccuracy: " + accuracy / Double.parseDouble(String.valueOf(counter)) * 100 + "%\n");
    }

    public int getNumFeatures() {
        return numFeatures;
    }
}
