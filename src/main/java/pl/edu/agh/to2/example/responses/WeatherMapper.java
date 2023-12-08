package pl.edu.agh.to2.example.responses;

import pl.edu.agh.to2.example.exceptions.RainClassifyingException;
import pl.edu.agh.to2.example.exceptions.SnowClassifyingException;
import pl.edu.agh.to2.example.exceptions.TemperatureException;
import pl.edu.agh.to2.example.exceptions.WindClassifyingException;
import pl.edu.agh.to2.example.model.Weather;

public class WeatherMapper {

    public WeatherMapper() {}

    public WeatherResponse createWeatherResponse(Weather weather) throws TemperatureException, WindClassifyingException, RainClassifyingException, SnowClassifyingException {
       return new WeatherResponse(
               weather.getDate(),
               weather.getTemperature(),
               weather.getFeelsLikeTemperature(),
               weather.getWindStrength(),
               weather.getPrecipitation()
       );
    }
}
