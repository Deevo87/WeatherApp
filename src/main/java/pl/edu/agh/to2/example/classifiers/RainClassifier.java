package pl.edu.agh.to2.example.classifiers;

import pl.edu.agh.to2.example.exceptions.RainClassifyingException;

import java.util.Map;
import java.util.TreeMap;

public class RainClassifier {
    private static final Map<Double, String> rainCategories = new TreeMap<>();

    private RainClassifier() {
        throw new IllegalStateException("Utility class");
    }

    static {
        rainCategories.put(2.5, "Light Rain");
        rainCategories.put(7.6, "Moderate Rain");
        rainCategories.put(50.0, "Heavy Rain");
        rainCategories.put(Double.POSITIVE_INFINITY, "Very Heavy Rain");
    }

    public static String classifyRain(double precipitationAmount) throws RainClassifyingException {
        for (Map.Entry<Double, String> entry : rainCategories.entrySet()) {
            if (precipitationAmount < entry.getKey()) {
                return entry.getValue();
            }
        }
        throw new RainClassifyingException("Couldn't classify rain by precipitation.");
    }

    public static int compareRainIntensity(String rain1, String rain2) {
        if (rain1.equals(rain2)) return 0;
        for (Map.Entry<Double, String> entry : rainCategories.entrySet()) {
            if (entry.getValue().equals(rain1)) {
                return 1;
            } else if (entry.getValue().equals(rain2)) {
                return -1;
            }
        }
        return 0;
    }
}
