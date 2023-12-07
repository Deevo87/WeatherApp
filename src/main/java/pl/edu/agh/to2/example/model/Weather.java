package pl.edu.agh.to2.example.model;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import pl.edu.agh.to2.example.exceptions.RainClassifyingException;
import pl.edu.agh.to2.example.exceptions.SnowClassifyingException;
import pl.edu.agh.to2.example.exceptions.TemperatureException;
import pl.edu.agh.to2.example.exceptions.WindClassifyingException;

import static pl.edu.agh.to2.example.classifiers.RainClassifier.classifyRain;
import static pl.edu.agh.to2.example.classifiers.SnowClassifier.classifySnow;
import static pl.edu.agh.to2.example.classifiers.TemperatureClassifier.classifyTemperature;
import static pl.edu.agh.to2.example.classifiers.WindClassifier.classifyWind;

public class Weather {
    private final LocalDateTime date;
    private final double temperature;
    private double feelsLikeTemperature;
    private final String condition; // for example: Light rain
    private final double windVelocityInKph;

    private final double precipitationAmount;

    public Weather(String date, double temperature, String condition, double windVelocityInKph, double precipitationAmount){
        this.date = parseStringDate(date);
        this.temperature = temperature;
        this.condition = condition;
        this.windVelocityInKph = windVelocityInKph;
        this.precipitationAmount = precipitationAmount;
    }

    private LocalDateTime parseStringDate(String dateToParse){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd [H][HH]:mm");
        return LocalDateTime.parse(dateToParse, formatter);
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getTemperature() throws TemperatureException {
        return classifyTemperature(temperature);
    }

    public double getFeelsLikeTemperature() {
        double v016 = Math.pow(temperature, 0.16);
        int scale = 10;
        this.feelsLikeTemperature = (double) Math.round((13.12 + 0.6215 * temperature - 11.37 * v016 + 0.3965 * temperature * v016) * scale) / scale;
        return feelsLikeTemperature;
    }

    private String getCondition() {
        return condition;
    }

    public String getWindStrength() throws WindClassifyingException {
        return classifyWind(windVelocityInKph);
    }

    private String getRainStrength() throws RainClassifyingException {
        return classifyRain(precipitationAmount);
    }

    private String getSnowStrength() throws SnowClassifyingException {
        return classifySnow(precipitationAmount);
    }

    public String getPrecipitation() throws SnowClassifyingException, RainClassifyingException {
        if (precipitationAmount == 0) {
            return "Lack of any precipitation.";
        }
        if (temperature <= 0.0) return getSnowStrength();
        return getRainStrength();
    }

    @Override
    public String toString() {
        return "Weather{" +
                "date='" + date + '\'' +
                ", temperature=" + temperature +
                ", feelsLikeTemperature=" + feelsLikeTemperature +
                ", condition='" + condition + '\'' +
                '}';
    }
}
