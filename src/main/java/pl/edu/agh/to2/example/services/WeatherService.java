package pl.edu.agh.to2.example.services;

import org.springframework.stereotype.Service;
import pl.edu.agh.to2.example.ConnectionData;
import pl.edu.agh.to2.example.exceptions.WeatherAppException;
import pl.edu.agh.to2.example.model.Weather;
import pl.edu.agh.to2.example.responses.WeatherResponse;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
public class WeatherService {
    private final String apiKey;
    private final String basicUrl;
    private final HttpClient httpClient;
    private final Parser parser = new Parser();

    public WeatherService() {
        this.apiKey = ConnectionData.API_KEY;
        this.basicUrl = ConnectionData.BASIC_URL;
        this.httpClient = HttpClient.newHttpClient();
    }

    public WeatherResponse getCurrentWeather(String city) throws Exception {
        String endpoint = "/current.json";
        String url = this.basicUrl + endpoint + "?key=" + this.apiKey + "&q=" + city + "&aqi=no";
        return this.parser.parseCurrentWeather(getWeatherResponse(url));
    }

    public List<List<Weather>> getForecastWeather(String city, int days) throws Exception {
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
}
