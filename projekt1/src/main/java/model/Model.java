package model;

import datamodel.DataObject;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Model {


    /**
     * Classifies DataObjects using the K-NN method
     *
     * @param trainingData List of traning data DataObjects
     * @param testObject   Object to be classified
     * @param k            k-nn parameter
     * @return classified objects label
     */
    public static String classify(List<DataObject> trainingData, DataObject testObject, int k) {

        List<DataObject> pq = findKNN(trainingData, testObject, k);

        Map<String, Integer> labelCount = countOccurrence(pq);

        return getHighestCountLabel(labelCount);
    }

    /**
     * @param trainingData List of traning data DataObjects
     * @param testObject   Object to be classified
     * @param k            k-nn parameter
     * @return PriorityQueue of k-nearest neightbors
     */
    private static List<DataObject> findKNN(List<DataObject> trainingData, DataObject testObject, int k) {
        List<DataObject> knn = new ArrayList<>(k);
        List<Double> distances = trainingData.stream()
                .mapToDouble(dataObject -> euclideanDistance(dataObject, testObject))
                .boxed()
                .collect(
                        Collectors.toCollection(() -> new ArrayList<>(trainingData.size()))
                );
        IntStream.range(0, k).map(i -> distances.indexOf(Collections.min(distances))).forEachOrdered(minIndex -> {
            knn.add(trainingData.get(minIndex));
            distances.set(minIndex, Double.MAX_VALUE);
        });
        return knn;
    }

    /**
     * @param labelCount map of counted label occurrence
     * @return Label with the highest count
     */
    private static String getHighestCountLabel(Map<String, Integer> labelCount) {
        String maxLabel = null;
        int maxCount = -1;

        for (String className : labelCount.keySet()) {
            int count = labelCount.get(className);
            if (count > maxCount) {
                maxCount = count;
                maxLabel = className;
            }
        }
        return maxLabel;
    }

    /**
     * Count the number of occurrences of each label among the k-nearest neighbors
     *
     * @param pq priority queue of DataObject
     * @return Counted occurances of each label
     */
    private static Map<String, Integer> countOccurrence(List<DataObject> pq) {

        Map<String, Integer> labelCount = new HashMap<>();
        for (DataObject dataObject : pq) {
            String className = dataObject.getClassName();
            labelCount.put(className, labelCount.getOrDefault(className, 0) + 1);
        }
        return labelCount;
    }

    /**
     * @param pointA DataObject A
     * @param pointB DataObject B
     * @return euclidean distance between both of those DataObjects
     */
    private static double euclideanDistance(DataObject pointA, DataObject pointB) {
        double sum = 0.0;
        for (int i = 0; i < pointA.getData().size(); i++) {
            double difference = pointA.getData().get(i) - pointB.getData().get(i);
            sum += difference * difference;
        }
        return Math.sqrt(sum);
    }
}
