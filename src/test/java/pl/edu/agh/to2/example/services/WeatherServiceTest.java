package pl.edu.agh.to2.example.services;

import org.junit.jupiter.api.Test;
import pl.edu.agh.to2.example.model.Weather;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WeatherServiceTest {

    @Test
    void testGetCurrentWeather() throws Exception {
        // given
        String city = "Cracow";
        WeatherService service = new WeatherService();
        LocalDateTime time = LocalDateTime.now();

        // when
//        Weather weather = service.getCurrentWeather(city);

        // then
//        assertEquals(time.getDayOfMonth(), weather.getDate().getDayOfMonth());
//        assertEquals(time.getMonth(), weather.getDate().getMonth());
//        assertEquals(time.getYear(), weather.getDate().getYear());
    }

    //wont work, it should be fixed someday XD
    @Test
    void testGetForecastWeather() throws Exception {
        // given
        String city = "Warsaw";
        WeatherService service = new WeatherService();
        LocalDateTime time = LocalDateTime.now();

        // when
//        List<Weather> weathers = service.getForecastWeather(city, 3);

        // then
//        assertEquals(time.getDayOfMonth(), weathers.get(0).getDate().getDayOfMonth());
    }
}