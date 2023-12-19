package pl.edu.agh.to2.example.DTOs;

import org.springframework.stereotype.Service;
import pl.edu.agh.to2.example.exceptions.RainClassifyingException;
import pl.edu.agh.to2.example.exceptions.SnowClassifyingException;
import pl.edu.agh.to2.example.exceptions.TemperatureException;
import pl.edu.agh.to2.example.exceptions.WindClassifyingException;
import pl.edu.agh.to2.example.model.Weather;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class WeatherDTOMapper {

    public WeatherDTO createWeatherResponse(Weather weather) throws TemperatureException, WindClassifyingException, RainClassifyingException, SnowClassifyingException {
       return new WeatherDTO(
               weather.getDate(),
               weather.getTemperature(),
               weather.getFeelsLikeTemperature(),
               weather.getWindStrength(),
               weather.getPrecipitation()
       );
    }

    public List<WeatherDTO> createWeatherResponse(List<Weather> weathers) {
        List<WeatherDTO> weatherDTOList = new ArrayList<>();
        weathers.forEach(weather -> {
            try {
                weatherDTOList.add(createWeatherResponse(weather));
            } catch (TemperatureException | WindClassifyingException | RainClassifyingException |
                     SnowClassifyingException e) {
                throw new RuntimeException(e);
            }
        });
        return weatherDTOList;
    }

    public WeatherDTO createWeatherResponse(LocalDateTime date, String temperature, double feelsLikeTemperature, String windStrength, String precipitation) {
        return new WeatherDTO(
                date,
                temperature,
                feelsLikeTemperature,
                windStrength,
                precipitation
        );
    }
}
