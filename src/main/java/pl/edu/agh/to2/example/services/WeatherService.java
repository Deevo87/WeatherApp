package pl.edu.agh.to2.example.services;

import org.springframework.stereotype.Service;
import pl.edu.agh.to2.example.ConnectionData;
import pl.edu.agh.to2.example.DTOs.ForecastWeatherDTO;
import pl.edu.agh.to2.example.DTOs.ForecastWeatherDTOMapper;
import pl.edu.agh.to2.example.DTOs.WeatherDTOMapper;
import pl.edu.agh.to2.example.exceptions.WeatherAppException;
import pl.edu.agh.to2.example.model.Weather;
import pl.edu.agh.to2.example.DTOs.WeatherDTO;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import static java.lang.Math.min;
import static java.lang.Math.max;
import static pl.edu.agh.to2.example.classifiers.RainClassifier.compareRainIntensity;
import static pl.edu.agh.to2.example.classifiers.TemperatureClassifier.classifyTemperature;
import static pl.edu.agh.to2.example.classifiers.WindClassifier.classifyWind;

@Service
public class WeatherService {
    private final String apiKey;
    private final String basicUrl;
    private final HttpClient httpClient;
    private final Parser parser;
    private final WeatherDTOMapper weatherDTOMapper;
    private final ForecastWeatherDTOMapper forecastWeatherDTOMapper;

    public WeatherService(Parser parser, WeatherDTOMapper weatherDTOMapper, ForecastWeatherDTOMapper forecastWeatherDTOMapper) {
        this.parser = parser;
        this.weatherDTOMapper = weatherDTOMapper;
        this.forecastWeatherDTOMapper = forecastWeatherDTOMapper;
        this.apiKey = ConnectionData.API_KEY;
        this.basicUrl = ConnectionData.BASIC_URL;
        this.httpClient = HttpClient.newHttpClient();
    }

    public WeatherDTO getCurrentWeather(String city) throws Exception {
        String endpoint = "/current.json";
        String url = this.basicUrl + endpoint + "?key=" + this.apiKey + "&q=" + city + "&aqi=no";
        return weatherDTOMapper.createWeatherResponse(this.parser.parseCurrentWeather(getWeatherResponse(url)));
    }

    private List<List<Weather>> getForecastWeather(String city, int days) throws Exception {
        String endpoint = "/forecast.json";
        String url = this.basicUrl + endpoint + "?key=" + this.apiKey + "&q=" + city + "&days=" + days + "&aqi=no&alerts=no";
        return this.parser.parseForecastWeather(getWeatherResponse(url));
    }

    private String getWeatherResponse(String url) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(java.net.URI.create(url))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            return response.body();
        } else {
            throw new WeatherAppException("Error with getting response from an API: " + response.statusCode());
        }
    }

    public List<List<ForecastWeatherDTO>> getTripConditions(String startLocation, String destinationLocation, int days) throws Exception {
        List<List<Weather>> forecastWeatherForStartLoc = getForecastWeather(startLocation, days);
        List<List<Weather>> forecastWeatherForDestLoc = getForecastWeather(destinationLocation, days);
        List<List<ForecastWeatherDTO>> tripForecast = new ArrayList<>();
        for (int i = 0; i < forecastWeatherForDestLoc.size(); i++) {
            List<ForecastWeatherDTO> day = new ArrayList<>();
            for (int j = 0; j < forecastWeatherForDestLoc.get(0).size(); j++) {
                Weather startLoc = forecastWeatherForStartLoc.get(i).get(j);
                Weather destLoc = forecastWeatherForDestLoc.get(i).get(j);
                String rainIntensity = switch(compareRainIntensity(startLoc.getRainStrength(), destLoc.getRainStrength())) {
                    case 1 -> startLoc.getRainStrength();
                    case -1 -> destLoc.getRainStrength();
                    default -> startLoc.getRainStrength();
                };
                String snowIntensity = switch(compareRainIntensity(startLoc.getRainStrength(), destLoc.getRainStrength())) {
                    case 1 -> startLoc.getSnowStrength();
                    case -1 -> destLoc.getSnowStrength();
                    default -> startLoc.getSnowStrength();
                };
                day.add(forecastWeatherDTOMapper.createForecastWeatherResponse(
                        startLoc.getDate(),
                        classifyTemperature(min(startLoc.getTemperatureDouble(), destLoc.getTemperatureDouble())),
                        min(startLoc.getFeelsLikeTemperature(), destLoc.getFeelsLikeTemperature()),
                        classifyWind(max(startLoc.getWindVelocityInKph(), destLoc.getWindVelocityInKph())),
                        rainIntensity,
                        snowIntensity
                ));
            }
            tripForecast.add(day);
        }
        return tripForecast;
    }
}
