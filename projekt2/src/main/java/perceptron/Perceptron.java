package perceptron;

import datamodel.DataModel;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Perceptron {

    private final double[] weights;
    private final double learningRate;
    private final Random random;

    /**
     * @param numFeatures specifies a number of input features
     * @param learningRate learning rate of an algorithm
     */
    public Perceptron(int numFeatures, double learningRate) {
        this.weights = new double[numFeatures];
        this.learningRate = learningRate;
        this.random = new Random();
        initializeWeights();
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
     * @param data list of DataModel objects containing features and class name
     * @param maxEpochs specifies maximum number of epochs to train the perceptron
     */
    public void train(List<DataModel> data, int maxEpochs) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("Iris-versicolor", 0);
        map.put("Iris-virginica", 1);

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
                int label = map.get(dataPoint.getClassName());

                //prediction based on the current weights
                double predictedLabel = predict(features);

                //if the prediction is not correct, flag is set to true and weights are updated
                if (predictedLabel != label) {
                    error = true;
                    updateWeights(features, label, predictedLabel);
                }
            }

            epoch++;
        } while (error && epoch < maxEpochs);
    }

    /**
     * Computes dot product of the weights to make the prediction
     * @param features array of doubles representing input features
     * @return If dot product is >= 0, return 0, else return 1
     */
    public int predict(List<Double> features) {
        double sum = 0.0;

        for (int i = 0; i < weights.length; i++) {
            sum += weights[i] * features.get(i);
        }

        return (sum >= 0.0) ? 1 : 0;
    }

    /**
     * Private helper to adjust weights based on the error in the prediction
     * @param features array of doubles representing input features
     * @param label an integer that represents the true label for the data point.
     * @param predictedLabel a double that represents the predicted label for the data point.
     */
    private void updateWeights(List<Double> features, int label, double predictedLabel) {

        //computes error difference as the difference between true label and predicted label
        double error = label - predictedLabel;

        //for each weight, method updates it by adding the product of learning rate, error and feature value
        for (int i = 0; i < weights.length; i++) {
            weights[i] += learningRate * error * features.get(i);
        }
    }
}
