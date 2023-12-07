package pl.edu.agh.to2.example.classifiers;

import pl.edu.agh.to2.example.exceptions.WindClassifyingException;

import java.util.Map;
import java.util.TreeMap;

public class WindClassifier {
    private static final Map<Double, String> windCategories = new TreeMap<>();

    static {
        windCategories.put(1.0, "Calm");
        windCategories.put(6.0, "Light Air");
        windCategories.put(12.0, "Light Breeze");
        windCategories.put(20.0, "Moderate Breeze");
        windCategories.put(29.0, "Fresh Breeze");
        windCategories.put(39.0, "Strong Breeze");
        windCategories.put(50.0, "Near Gale");
        windCategories.put(62.0, "Gale");
        windCategories.put(75.0, "Strong Gale");
        windCategories.put(89.0, "Storm");
        windCategories.put(103.0, "Violent Storm");
        windCategories.put(Double.POSITIVE_INFINITY, "Hurricane");
    }

    public static String classifyWind(double windVelocityInKph) throws WindClassifyingException {
        for (Map.Entry<Double, String> entry : windCategories.entrySet()) {
            if (windVelocityInKph < entry.getKey()) {
                return entry.getValue();
            }
        }
        throw new WindClassifyingException("Couldn't classify wind by velocity.");
    }

}
