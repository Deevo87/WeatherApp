package pl.edu.agh.to2.example.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class WeatherTest {

    @Test
    void testCreation() {
        // given
        String date = "2023-10-28 12:40";
        double temperature = 21.3;
        String condition = "Light rain";
        double windVelocityInKph = 6.1;
        double precipitationAmount = 1.1;

        // when
        Weather weather = new Weather(date, temperature, condition, windVelocityInKph, precipitationAmount);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd [H][HH]:mm");
        LocalDateTime expectedDate = LocalDateTime.parse(date, formatter);

        // then
        assertEquals(expectedDate, weather.getDate());
        assertEquals(temperature, weather.getTemperatureDouble());
        assertEquals(condition, weather.getCondition());
    }
}
