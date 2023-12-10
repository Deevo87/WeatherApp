package pl.edu.agh.to2.example.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.edu.agh.to2.example.exceptions.RainClassifyingException;
import pl.edu.agh.to2.example.exceptions.SnowClassifyingException;
import pl.edu.agh.to2.example.exceptions.TemperatureException;
import pl.edu.agh.to2.example.exceptions.WindClassifyingException;
import pl.edu.agh.to2.example.model.Weather;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ParserTest {

    @Autowired
    Parser parser;

    @Test
    void parseCurrentWeatherTest() throws WindClassifyingException, TemperatureException, RainClassifyingException, SnowClassifyingException {
        double temperature = 10.0;
        double windVelocityInKph = 19.1;
        double precipitationAmount = 0.09;
        String condition =  "Light rain";
        LocalDateTime localDateTime = LocalDateTime.parse("2023-12-10 14:05".replace(" ", "T"));

        //given
        String response = MockResponses.currentWeatherResponse;

        //when
        Weather weather = parser.parseCurrentWeather(response);

        //then
        assertEquals(temperature, weather.getTemperatureDouble()) ;
        assertEquals(windVelocityInKph, weather.getWindVelocityInKph());
        assertEquals(precipitationAmount, weather.getPrecipitationAmount());
        assertEquals(condition, weather.getCondition());
        assertEquals(localDateTime, weather.getDate());
    }

    @Test
    void parseForecastWeatherTest(){

        double temperature = 8.6;
        double windVelocityInKph = 31.3;
        double precipitationAmount = 0.0;
        String condition =  "Clear";
        LocalDateTime localDateTime = LocalDateTime.parse("2023-12-10 00:00".replace(" ", "T"));

        //given
        String response = MockResponses.forecastWeatherResponse;

        //when
        List<List<Weather>> hourWeatherList = parser.parseForecastWeather(response);
        Weather weather = hourWeatherList.get(0).get(0);

        //then
        assertEquals(temperature, weather.getTemperatureDouble()) ;
        assertEquals(windVelocityInKph, weather.getWindVelocityInKph());
        assertEquals(precipitationAmount, weather.getPrecipitationAmount());
        assertEquals(condition, weather.getCondition());
        assertEquals(localDateTime, weather.getDate());

    }
}
