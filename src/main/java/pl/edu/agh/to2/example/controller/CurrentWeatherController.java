package pl.edu.agh.to2.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.to2.example.exceptions.WeatherAppException;
import pl.edu.agh.to2.example.DTOs.WeatherDTO;
import pl.edu.agh.to2.example.services.WeatherService;

@RestController
@RequestMapping("api")
public class CurrentWeatherController {
    private final WeatherService weatherService;

    public CurrentWeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }


    @GetMapping("/currentWeather/{city}")
    public WeatherDTO getCurrentWeather(@PathVariable String city) throws WeatherAppException {
        try {
            return weatherService.getCurrentWeather(city);
        } catch (Exception e) {
            throw new WeatherAppException(e);
        }
    }
}
