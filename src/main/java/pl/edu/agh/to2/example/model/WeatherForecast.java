package pl.edu.agh.to2.example.model;

import java.util.List;

public class WeatherForecast {
    private List<Weather> weatherForecasts;

    public List<Weather> getWeatherForecasts() {
        return weatherForecasts;
    }

    public void setWeatherForecasts(List<Weather> weatherForecasts) {
        this.weatherForecasts = weatherForecasts;
    }

    @Override
    public String toString() {
        return "WeatherForecast{" +
                "weatherForecasts=" + weatherForecasts +
                '}';
    }
}
