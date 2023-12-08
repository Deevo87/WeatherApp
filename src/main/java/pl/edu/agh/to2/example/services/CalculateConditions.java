package pl.edu.agh.to2.example.services;

import pl.edu.agh.to2.example.model.Weather;

import java.util.List;

public class CalculateConditions {
    private final WeatherService weatherService;
    private List<List<Weather>> weathers;

    private double lowestTemperature = Double.POSITIVE_INFINITY;
    private double lowestFeelLikeTemperature = Double.POSITIVE_INFINITY;
    private String strongestRain;
    private String strongestSnow;
    private String strongestWind;

    public CalculateConditions(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    public void setForecastList(List<List<Weather>> weathers) {
        this.weathers = weathers;
    }
//    private double calculateTemperature() {
//
//    }
}
