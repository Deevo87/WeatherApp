package pl.edu.agh.to2.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.to2.example.DTOs.ForecastWeatherDTO;
import pl.edu.agh.to2.example.services.WeatherService;

import java.util.List;

@RestController

public class ForecastWeatherController {
    private final WeatherService weatherService;

    public ForecastWeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

//    @GetMapping({"forecastWeather/{startLocation}/{destLocation}/{days}"})
//    public List<List<ForecastWeatherDTO>> getForecastWeather(@PathVariable String startLocation, @PathVariable String destLocation, @PathVariable int days) throws Exception {
//        return weatherService.getTripConditions(startLocation, destLocation, days);
//    }

    @GetMapping({"forecastWeather/{locations}/{days}"})
    public List<List<ForecastWeatherDTO>> getForecastWeather( @PathVariable List<String> locations, @PathVariable int days) throws Exception {
        return weatherService.getTripConditions(locations, days);
    }
}
