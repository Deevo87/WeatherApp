package pl.edu.agh.to2.example;

import pl.edu.agh.to2.example.model.Weather;
import pl.edu.agh.to2.example.model.WeatherForecast;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WeatherService {
    private final String apiKey;
    private final String basicUrl;
    private final HttpClient httpClient;

    public WeatherService() {
        this.apiKey = ConnectionData.API_KEY;
        this.basicUrl = ConnectionData.BASIC_URL;
        this.httpClient = HttpClient.newHttpClient();
    }

    public Weather getCurrentWeather(String city) throws Exception {
        String endpoint = "/current.json";
        String url = this.basicUrl + endpoint + "?key=" + this.apiKey + "&q=" + city + "&aqi=no";
        String response = getWeatherResponse(url);
        return parseCurrentWeather(response);
    }

    public WeatherForecast getForecastWeather(String city, int days) throws Exception {
        String endpoint = "/forecast.json";
        String url = this.basicUrl + endpoint + "?key" + this.apiKey + "&q=" + city + "&days=" + days + "aqi=no&alerts=no";
        String response = getWeatherResponse(url);
        return parseForecastWeather(response);
    }

    private String getWeatherResponse(String url) throws Exception {
        System.out.println(url);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(java.net.URI.create(url))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            return response.body();
        } else {
            throw new Exception("Error with getting response from an API: " + response.statusCode());
        }
    }

    // TODO
    private Weather parseCurrentWeather(String response) {
        return new Weather();
    }

    // TODO
    private WeatherForecast parseForecastWeather(String response) {
        return new WeatherForecast();
    }
}
