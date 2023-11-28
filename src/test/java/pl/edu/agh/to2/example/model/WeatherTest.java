package pl.edu.agh.to2.example.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class WeatherTest {

    @Test
    void testCreation() {
        // given
        String date = "2023-10-28 12:40";
        double temperature = 21.3;
        double feelsLikeTemperature = 22.7;
        String condition = "Light rain";

        // when
        Weather weather = new Weather(date, temperature, feelsLikeTemperature, condition);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd [H][HH]:mm");
        LocalDateTime expectedDate = LocalDateTime.parse(date, formatter);

        // then
        assertEquals(expectedDate, weather.getDate());
        assertEquals(temperature, weather.getTemperature());
        assertEquals(feelsLikeTemperature, weather.getFeelsLikeTemperature());
        assertEquals(condition, weather.getCondition());
    }
}
