package pl.edu.agh.to2.example.DTOs;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
public class ForecastWeatherDTOMapper {

    public ForecastWeatherDTO createForecastWeatherResponse(LocalDateTime date, String temperature, double feelsLikeTemperature,
                                                            String windStrength, String rainStrength, String snowStrength, boolean mud) {
        return new ForecastWeatherDTO(
                date,
                temperature,
                feelsLikeTemperature,
                windStrength,
                rainStrength,
                snowStrength,
                mud
        );
    }
}