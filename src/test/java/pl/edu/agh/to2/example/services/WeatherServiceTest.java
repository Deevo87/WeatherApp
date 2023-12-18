package pl.edu.agh.to2.example.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.edu.agh.to2.example.dtos.WeatherDTO;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WeatherServiceTest {

    @Autowired
    WeatherService service;

    @Test
    void testGetCurrentWeather() throws Exception {
        //given
        String city = "Cracow";
        LocalDateTime time = LocalDateTime.now();

        //when
        WeatherDTO weather = service.getCurrentWeather(city);

        //then
        assertEquals(time.getDayOfMonth(), weather.getDate().getDayOfMonth());
        assertEquals(time.getMonth(), weather.getDate().getMonth());
        assertEquals(time.getYear(), weather.getDate().getYear());

        assertTrue(true);
    }

}