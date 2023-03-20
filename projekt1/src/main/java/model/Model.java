package model;

import datamodel.DataObject;

import java.util.*;
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

        PriorityQueue<DataObject> pq = findKNN(trainingData, testObject, k);

        Map<String, Integer> labelCount = countOccurances(pq);

        return getHighestCountLabel(labelCount);
    }

    /**
     * @param trainingData List of traning data DataObjects
     * @param testObject   Object to be classified
     * @param k            k-nn parameter
     * @return PriorityQueue of k-nearest neightbors
     */
    private static PriorityQueue<DataObject> findKNN(List<DataObject> trainingData, DataObject testObject, int k) {
        PriorityQueue<DataObject> pq = new PriorityQueue<>(k, Comparator.comparingDouble(a -> euclideanDistance(a, testObject)));
        trainingData.forEach(dataObject -> {
            pq.offer(dataObject);
            if (pq.size() > k) {
                pq.poll();
            }
        });
        return pq;
    }

    /**
     * @param labelCount map of counted labele occurances
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
    private static Map<String, Integer> countOccurances(PriorityQueue<DataObject> pq) {

        Map<String, Integer> labelCount = new HashMap<>();
        for (DataObject dataObject : pq) {
            labelCount.put(dataObject.getClassName(), labelCount.getOrDefault(dataObject.getClassName(), 0));
        }
        return labelCount;
    }

    /**
     * @param pointA DataObject A
     * @param pointB DataObject B
     * @return euclidean distance between both of those DataObjects
     */
    private static double euclideanDistance(DataObject pointA, DataObject pointB) {
        double sum = IntStream.range(0, pointA.getData().size()).mapToDouble(i -> Math.pow(pointA.getData().get(i) - pointB.getData().get(i), 2)).sum();
        return Math.sqrt(sum);
    }
}
