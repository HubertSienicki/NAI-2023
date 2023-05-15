import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class KMeans {

    private static final String IRIS_DATA_FILE = "C:\\Users\\kneiv\\IdeaProjects\\NAI-2023\\kmeans-classifier\\src\\main\\java\\iris.data";

    /**
     * Main method to run the program.
     *
     * @param args The command line arguments
     */
    public static void main(String[] args) {
        int k = 3; // Choose the value of k
        List<Iris> dataset = readIrisDataFromFile(IRIS_DATA_FILE);
        List<Cluster> clusters = kMeans(dataset, k);
        printResults(clusters);
    }

    /**
     * Read the Iris data file and return a list of Iris instances.
     *
     * @param filename The name of the Iris data file
     * @return A list of Iris instances
     */
    public static List<Iris> readIrisDataFromFile(String filename) {
        List<Iris> dataset = new ArrayList<>();
        Path filePath = Paths.get(filename);

        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                double sepalLength = Double.parseDouble(values[0]);
                double sepalWidth = Double.parseDouble(values[1]);
                double petalLength = Double.parseDouble(values[2]);
                double petalWidth = Double.parseDouble(values[3]);
                String label = values[4];  // Add this line
                dataset.add(new Iris(sepalLength, sepalWidth, petalLength, petalWidth, label));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataset;
    }

    /**
     * Implementation of the k-means algorithm.
     *
     * @param dataset The list of Iris instances to cluster
     * @param k       The number of clusters to create
     * @return A list of clusters
     */
    public static List<Cluster> kMeans(List<Iris> dataset, int k) {
        List<Cluster> clusters = initializeClusters(dataset, k);
        double prevDistanceSum = Double.MAX_VALUE;
        double currentDistanceSum;

        while (true) {
            assignIrisToClosestCluster(dataset, clusters);

            currentDistanceSum = 0.0;
            for (Cluster cluster : clusters) {
                currentDistanceSum += cluster.updateCentroid();
            }

            System.out.printf("Iteracja: %.2f\n", currentDistanceSum);

            if (Math.abs(currentDistanceSum - prevDistanceSum) <= 1e-9) {
                break;
            }

            prevDistanceSum = currentDistanceSum;
            clearClusters(clusters);
        }
        return clusters;
    }

    /**
     * Initialize clusters by randomly selecting k instances from the dataset as initial centroids.
     *
     * @param dataset The list of Iris instances to cluster
     * @param k       The number of clusters to create
     * @return A list of initialized clusters
     */
    public static List<Cluster> initializeClusters(List<Iris> dataset, int k) {
        List<Cluster> clusters = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < k; i++) {
            int randomIndex = rand.nextInt(dataset.size());
            clusters.add(new Cluster(dataset.get(randomIndex)));
        }
        return clusters;
    }

    /**
     * Assign each Iris instance to its closest cluster.
     *
     * @param dataset  The list of Iris instances to cluster
     * @param clusters The list of clusters
     */
    public static void assignIrisToClosestCluster(List<Iris> dataset, List<Cluster> clusters) {
        for (Iris iris : dataset) {
            Cluster closestCluster = null;
            double minDistance = Double.MAX_VALUE;
            for (Cluster cluster : clusters) {
                double distance = cluster.getCentroid().distanceTo(iris);
                if (distance < minDistance) {
                    minDistance = distance;
                    closestCluster = cluster;
                }
            }
            closestCluster.addIris(iris);
        }
    }

    /**
     * Clear all clusters' Iris instances.
     *
     * @param clusters The list of clusters to be cleared
     */
    public static void clearClusters(List<Cluster> clusters) {
        for (Cluster cluster : clusters) {
            cluster.clear();
        }
    }

    /**
     * Print the final results of the clustering.
     *
     * @param clusters The list of clusters
     */
    public static void printResults(List<Cluster> clusters) {
        for (int i = 0; i < clusters.size(); i++) {
            Cluster cluster = clusters.get(i);
            System.out.println("Grupa " + (i + 1) + ":");
            System.out.println("Centroid: " + cluster.getCentroid());
            for (Iris iris : cluster.getIrisList()) {
                System.out.println(iris);
            }
            System.out.println();

            Map<String, Double> labelPercentages = cluster.calculateLabelPercentages();
            System.out.println("Label percentages:");
            for (Map.Entry<String, Double> entry : labelPercentages.entrySet()) {
                System.out.printf("%s: %.2f%%\n", entry.getKey(), entry.getValue());
            }

            double entropy = cluster.calculateEntropy();
            System.out.printf("Entropy: %.2f\n", entropy);
        }
    }
}
