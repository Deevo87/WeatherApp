package pl.edu.agh.to2.example.classifiers;

import pl.edu.agh.to2.example.exceptions.SnowClassifyingException;

import java.util.Map;
import java.util.TreeMap;

public class SnowClassifier {

    private static final Map<Double, String> snowCategories = new TreeMap<>();

    private SnowClassifier() {
        throw new IllegalStateException("Utility class");
    }

    static {
        snowCategories.put(1.0, "Very Light Snow");
        snowCategories.put(5.0, "Light Snow");
        snowCategories.put(10.0, "Moderate Snow");
        snowCategories.put(20.0, "Heavy Snow");
        snowCategories.put(Double.POSITIVE_INFINITY, "Very Heavy Snow");
    }

    public static String classifySnow(double precipitationAmount) throws SnowClassifyingException {
        for (Map.Entry<Double, String> entry : snowCategories.entrySet()) {
            if (precipitationAmount < entry.getKey()) {
                return entry.getValue();
            }
        }
        throw new SnowClassifyingException("Couldn't classify snowy by precipitation.");
    }

    public static int compareSnowIntensity(String snow1, String snow2) {
        if (snow1.equals(snow2)) return 0;
        for (Map.Entry<Double, String> entry : snowCategories.entrySet()) {
            if (entry.getValue().equals(snow1)) {
                return 1;
            } else if (entry.getValue().equals(snow2)) {
                return -1;
            }
        }
        return 0;
    }
}
