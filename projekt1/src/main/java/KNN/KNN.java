package KNN;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

public class KNN {
    private static final int K = 3; // You can choose the value of K
    private static final String TRAINING_CSV_FILE = "projekt1/data/wdbc.data";
    private static final String TEST_CSV_FILE = "projekt1/data/wdbc.test.data";

    public static void main(String[] args) throws IOException {
        List<DataPoint> trainingDataset = loadCSVData(TRAINING_CSV_FILE);
        List<DataPoint> testDataset = loadCSVData(TEST_CSV_FILE);

        int correctPredictions = 0;
        int totalPredictions = 0;

        // Classify test dataset
        for (DataPoint dataPoint : testDataset) {
            String predictedLabel = classify(trainingDataset, dataPoint.getDatapoints(), K);
            totalPredictions++;

            if (predictedLabel.equals(dataPoint.getLabel())) {
                correctPredictions++;
            }

            double accuracy = (double) correctPredictions / totalPredictions * 100;
            System.out.println("Input: " + Arrays.toString(dataPoint.getDatapoints()) + ", Predicted Label: " + predictedLabel + ", Actual Label: " + dataPoint.getLabel() + ", Accuracy: " + accuracy + "%");
        }

        // Classify user-input array of datapoints and a label
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of datapoints:");
        int n = scanner.nextInt();
        double[] userDatapoints = new double[n];

        System.out.println("Enter the datapoints separated by space:");
        for (int i = 0; i < n; i++) {
            userDatapoints[i] = scanner.nextDouble();
        }

        System.out.println("Enter the label:");
        String userLabel = scanner.next();

        String predictedUserLabel = classify(trainingDataset, userDatapoints, K);
        totalPredictions++;

        if (predictedUserLabel.equals(userLabel)) {
            correctPredictions++;
        }

        double accuracy = (double) correctPredictions / totalPredictions * 100;
        System.out.println("User Input: " + Arrays.toString(userDatapoints) + ", Predicted Label: " + predictedUserLabel + ", Actual Label: " + userLabel + ", Accuracy: " + accuracy + "%");

        scanner.close();
    }

    private static List<DataPoint> loadCSVData(String csvFile) throws IOException {
        List<DataPoint> dataset = new ArrayList<>();

        try (Reader in = new FileReader(csvFile)) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(in);

            for (CSVRecord record : records) {
                double[] datapoints = new double[record.size() - 1];
                for (int i = 0; i < record.size() - 1; i++) {
                    datapoints[i] = Double.parseDouble(record.get(i));
                }
                String label = record.get(record.size() - 1);
                dataset.add(new DataPoint(datapoints, label));
            }
        }

        return dataset;
    }

    private static String classify(List<DataPoint> dataset, double[] input, int k) {
        List<DistanceLabel> distances = new ArrayList<>();

        for (DataPoint data : dataset) {
            double distance = euclideanDistance(data.getDatapoints(), input);
            distances.add(new DistanceLabel(distance, data.getLabel()));
        }

        Collections.sort(distances, Comparator.comparingDouble(DistanceLabel::getDistance));

        Map<String, Integer> labelCounts = new HashMap<>();

        for (int i = 0; i < k; i++) {
            DistanceLabel neighbor = distances.get(i);
            labelCounts.put(neighbor.getLabel(), labelCounts.getOrDefault(neighbor.getLabel(), 0) + 1);
        }

        return Collections.max(labelCounts.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
    }

    private static double euclideanDistance(double[] a, double[] b) {
        double sum = 0;

        for (int i = 0; i < a.length; i++) {
            sum += Math.pow(a[i] - b[i], 2);
        }
        return Math.sqrt(sum);
    }

    static class DataPoint {
        private final double[] datapoints;
        private final String label;

        public DataPoint(double[] datapoints, String label) {
            this.datapoints = datapoints;
            this.label = label;
        }

        public double[] getDatapoints() {
            return datapoints;
        }

        public String getLabel() {
            return label;
        }
    }

    static class DistanceLabel {
        private final double distance;
        private final String label;

        public DistanceLabel(double distance, String label) {
            this.distance = distance;
            this.label = label;
        }

        public double getDistance() {
            return distance;
        }

        public String getLabel() {
            return label;
        }
    }
}
