package pl.edu.agh.to2.example.dtos;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.edu.agh.to2.example.exceptions.RainClassifyingException;
import pl.edu.agh.to2.example.exceptions.SnowClassifyingException;
import pl.edu.agh.to2.example.exceptions.TemperatureException;
import pl.edu.agh.to2.example.exceptions.WindClassifyingException;
import pl.edu.agh.to2.example.model.Weather;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class WeatherDTOMapperTest {

    @Autowired
    WeatherDTOMapper weatherDTOMapper;

    @Test
    void createWeatherResponseTest() throws WindClassifyingException, TemperatureException, RainClassifyingException, SnowClassifyingException {
        //given
        double temperature = 5.0;
        double windVelocityInKph = 19.1;
        double precipitationAmount = 0.09;
        String condition =  "not important";
        String stringDate = "2023-12-10 14:05";
        LocalDateTime localDateTime = LocalDateTime.parse(stringDate.replace(" ", "T"));

        Weather weather = new Weather(stringDate, temperature, condition, windVelocityInKph, precipitationAmount);

        //when
        WeatherDTO weatherDTO = weatherDTOMapper.createWeatherResponse(weather);

        //then
        assertEquals("Warm", weatherDTO.getTemperature());
        assertEquals("Moderate Breeze", weatherDTO.getWindStrength());
        assertEquals("Light Rain", weatherDTO.getPrecipitation());
        assertEquals(localDateTime, weatherDTO.getDate());


    }
}
