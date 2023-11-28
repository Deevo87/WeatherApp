package pl.edu.agh.to2.example.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Weather {
    private LocalDateTime date;
    private double temperature;
    private double feelsLikeTemperature;
    private String condition; // for example: Light rain

    public Weather(String date, double temperature, double feelsLikeTemperature, String condition){
        this.date = parseStringDate(date);
        this.temperature = temperature;
        this.feelsLikeTemperature = feelsLikeTemperature;
        this.condition = condition;
    }

    private LocalDateTime parseStringDate(String dateToParse){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd [H][HH]:mm");
        LocalDateTime parsedDateTime = LocalDateTime.parse(dateToParse, formatter);
        return parsedDateTime;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = parseStringDate(date);
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getFeelsLikeTemperature() {
        return feelsLikeTemperature;
    }

    public void setFeelsLikeTemperature(double feelsLikeTemperature) {
        this.feelsLikeTemperature = feelsLikeTemperature;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
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
