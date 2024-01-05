package pl.edu.agh.to2.example.DTOs;

import java.time.LocalDateTime;

public class ForecastWeatherDTO {
    private final LocalDateTime date;
    private final String temperature;
    private final double feelsLikeTemperature;
    private final String windStrength;
    private final String rainStrength;
    private final String snowStrength;
    private final boolean isMud;


    public ForecastWeatherDTO(LocalDateTime date, String temperature, double feelsLikeTemperature, String windStrength, String rainStrength, String snowStrength, boolean isMud) {
        this.date = date;
        this.temperature = temperature;
        this.feelsLikeTemperature = feelsLikeTemperature;
        this.windStrength = windStrength;
        this.rainStrength = rainStrength;
        this.snowStrength = snowStrength;
        this.isMud = isMud;
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

    public String getRainStrength() {
        return rainStrength;
    }

    public String getSnowStrength() {
        return snowStrength;
    }

    public boolean isMud() {
        return isMud;
    }
}
