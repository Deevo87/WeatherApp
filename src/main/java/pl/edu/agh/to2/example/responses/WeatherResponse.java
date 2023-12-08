package pl.edu.agh.to2.example.responses;

import java.time.LocalDateTime;

public class WeatherResponse {
    private final LocalDateTime date;
    private final String temperature;
    private final double feelsLikeTemperature;
    private final String windStrength;
    private final String precipitation;

    public WeatherResponse(LocalDateTime date, String temperature, double feelsLikeTemperature, String windStrength, String precipitation) {
        this.date = date;
        this.temperature = temperature;
        this.feelsLikeTemperature = feelsLikeTemperature;
        this.windStrength = windStrength;
        this.precipitation = precipitation;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getTemperature() {
        return temperature;
    }

    public double getFeelsLikeTemperature() {
        return feelsLikeTemperature;
    }

    public String getWindStrength() {
        return windStrength;
    }

    public String getPrecipitation() {
        return precipitation;
    }
}
