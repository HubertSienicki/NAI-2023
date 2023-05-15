import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cluster {
    private Iris centroid;
    private List<Iris> irisList;

    public Cluster(Iris centroid) {
        this.centroid = centroid;
        this.irisList = new ArrayList<>();
    }

    public void addIris(Iris iris) {
        irisList.add(iris);
    }

    public void clear() {
        irisList.clear();
    }

    public double updateCentroid() {
        double newSepalLength = 0;
        double newSepalWidth = 0;
        double newPetalLength = 0;
        double newPetalWidth = 0;

        for (Iris iris : irisList) {
            newSepalLength += iris.x1;
            newSepalWidth += iris.x2;
            newPetalLength += iris.x3;
            newPetalWidth += iris.x4;
        }
        newSepalLength /= irisList.size();
        newSepalWidth /= irisList.size();
        newPetalLength /= irisList.size();
        newPetalWidth /= irisList.size();

        Iris newCentroid = new Iris(newSepalLength, newSepalWidth, newPetalLength, newPetalWidth);

        double distanceSum = 0;
        for (Iris iris : irisList) {
            distanceSum += iris.distanceTo(newCentroid);
        }

        centroid = newCentroid;
        return distanceSum;
    }

    public Map<String, Double> calculateLabelPercentages() {
        Map<String, Integer> labelCounts = new HashMap<>();
        for (Iris iris : irisList) {
            labelCounts.put(iris.getLabel(), labelCounts.getOrDefault(iris.getLabel(), 0) + 1);
        }

        Map<String, Double> labelPercentages = new HashMap<>();
        for (Map.Entry<String, Integer> entry : labelCounts.entrySet()) {
            labelPercentages.put(entry.getKey(), 100.0 * entry.getValue() / irisList.size());
        }

        return labelPercentages;
    }

    public double calculateEntropy() {
        Map<String, Double> labelPercentages = calculateLabelPercentages();
        double entropy = 0.0;
        for (double percentage : labelPercentages.values()) {
            double p = percentage / 100.0;
            entropy -= p * Math.log(p) / Math.log(2);
        }
        return entropy;
    }

    public Iris getCentroid() {
        return centroid;
    }

    public List<Iris> getIrisList() {
        return irisList;
    }

    @Override
    public String toString() {
        return "Cluster{" + "centroid=" + centroid + ", irisList=" + irisList + '}';
    }
}