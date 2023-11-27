package pl.edu.agh.to2.example;

import org.json.JSONArray;
import pl.edu.agh.to2.example.model.Weather;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

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

    public List<Weather> getForecastWeather(String city, int days) throws Exception {
        String endpoint = "/forecast.json";
        String url = this.basicUrl + endpoint + "?key=" + this.apiKey + "&q=" + city + "&days=" + days + "&aqi=no&alerts=no";
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
            System.out.println(response.body());
            return response.body();
        } else {
            throw new Exception("Error with getting response from an API: " + response.statusCode());
        }
    }

    private Weather parseCurrentWeather(String response) {
        JSONObject jsonObject = new JSONObject(response);

        JSONObject location = jsonObject.getJSONObject("location");
        String localtime = location.getString("localtime");

        JSONObject current = jsonObject.getJSONObject("current");
        double temperature = current.getDouble("temp_c");
        double feelsLikeTemperature = current.getDouble("feelslike_c");

        JSONObject condition = current.getJSONObject("condition");
        String conditionText = condition.getString("text");

        Weather weather = new Weather(localtime, temperature, feelsLikeTemperature, conditionText);

        return weather;
    }

    private List<Weather> parseForecastWeather(String response) {
        List<Weather> weathers = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(response);
        JSONObject forecast = jsonObject.getJSONObject("forecast");
        JSONArray forecastDaysArray = forecast.getJSONArray("forecastday"); // tablica dni

        //ide po dniach
        for(int dayNumber = 0; dayNumber < forecastDaysArray.length(); dayNumber++){
            JSONObject forecastDay = forecastDaysArray.getJSONObject(dayNumber);
            JSONArray hoursArray = forecastDay.getJSONArray("hour"); //tablica godzin w dniu

            //ide po godzinach w danym dniu
            for(int hourNumber = 0; hourNumber < hoursArray.length(); hourNumber++){
                JSONObject hour = hoursArray.getJSONObject(hourNumber);

                String time = hour.getString("time");
                Double temperature = hour.getDouble("temp_c");
                Double feelsLikeTemperature = hour.getDouble("feelslike_c");

                JSONObject condition = hour.getJSONObject("condition");
                String conditionText = condition.getString("text");

                Weather weather = new Weather(time, temperature, feelsLikeTemperature, conditionText);

                weathers.add(weather);
            }
        }

        return weathers;
    }
}
