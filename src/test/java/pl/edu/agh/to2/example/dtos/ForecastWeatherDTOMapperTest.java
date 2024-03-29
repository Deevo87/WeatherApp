package pl.edu.agh.to2.example.dtos;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.edu.agh.to2.example.DTOs.ForecastWeatherDTO;
import pl.edu.agh.to2.example.DTOs.ForecastWeatherDTOMapper;
import pl.edu.agh.to2.example.exceptions.RainClassifyingException;
import pl.edu.agh.to2.example.exceptions.SnowClassifyingException;
import pl.edu.agh.to2.example.exceptions.TemperatureException;
import pl.edu.agh.to2.example.exceptions.WindClassifyingException;
import pl.edu.agh.to2.example.model.Weather;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ForecastWeatherDTOMapperTest {


    @Autowired
    ForecastWeatherDTOMapper forecastWeatherDTOMapper;

    @Test
    void createWeatherResponseTest() throws WindClassifyingException, TemperatureException, RainClassifyingException, SnowClassifyingException {
        //given
        double temperature = 5.0;
        double windVelocityInKph = 19.1;
        double precipitationAmount = 0.09;
        String condition =  "not important";
        String stringDate = "2023-12-10 14:05";
        LocalDateTime localDateTime = LocalDateTime.parse(stringDate.replace(" ", "T"));
        boolean isMud = true;

        Weather weather = new Weather(stringDate, temperature, condition, windVelocityInKph, precipitationAmount);

        //when
        ForecastWeatherDTO forecastWeatherDTO = forecastWeatherDTOMapper.createForecastWeatherResponse(weather.getDate(),
                weather.getTemperature(), weather.getFeelsLikeTemperature(), weather.getWindStrength(), weather.getRainStrength(),
                weather.getSnowStrength(), isMud);

        //then
        assertEquals("Warm", forecastWeatherDTO.getTemperature());
        assertEquals("Moderate Breeze", forecastWeatherDTO.getWindStrength());
        assertEquals("Light Rain", forecastWeatherDTO.getRainStrength());
        assertEquals(localDateTime, forecastWeatherDTO.getDate());
        assertEquals(isMud, Boolean.valueOf(forecastWeatherDTO.getMud()));
    }}