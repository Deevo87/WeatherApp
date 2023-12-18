package pl.edu.agh.to2.example.classifiers;

import pl.edu.agh.to2.example.exceptions.TemperatureException;

import java.util.Map;
import java.util.TreeMap;

public class TemperatureClassifier {

    private final static Map<Double, String> temperatureCategories = new TreeMap<>();

    private TemperatureClassifier() {
        throw new IllegalStateException("Utility class");
    }

    static {
        temperatureCategories.put(-31.0, "Extremely Cold");
        temperatureCategories.put(-15.0, "Very Cold");
        temperatureCategories.put(0.0, "Cold");
        temperatureCategories.put(10.0, "Warm");
        temperatureCategories.put(30.0, "Hot");
        temperatureCategories.put(40.0, "Extremely Hot");
        temperatureCategories.put(50.0, "Living Hell");
        temperatureCategories.put(Double.POSITIVE_INFINITY, "Hell's last floor");
    }

    public static String classifyTemperature(double temperature) throws TemperatureException {
        for (Map.Entry<Double, String> entry : temperatureCategories.entrySet()) {
            if (temperature < entry.getKey()) {
                return entry.getValue();
            }
        }
        throw new TemperatureException("Couldn't classify temperature.");
    }
}
