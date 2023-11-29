package pl.edu.agh.to2.example.services;

import org.json.JSONArray;
import org.json.JSONObject;
import pl.edu.agh.to2.example.model.Weather;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    public Parser() {
    }

    public List<List<Weather>> parseForecastWeather(String response) {
        List<List<Weather>> weathersForEachDay = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(response);
        JSONObject forecast = jsonObject.getJSONObject("forecast");
        JSONArray forecastDaysArray = forecast.getJSONArray("forecastday");

        forecastDaysArray.forEach((day) -> {
           weathersForEachDay.add(getWeatherFromDay((JSONObject) day));
        });
        return weathersForEachDay;
    }

    public Weather parseCurrentWeather(String response) {
        JSONObject jsonObject = new JSONObject(response);

        JSONObject location = jsonObject.getJSONObject("location");
        String localtime = location.getString("localtime");

        JSONObject current = jsonObject.getJSONObject("current");
        double temperature = current.getDouble("temp_c");
        double feelsLikeTemperature = current.getDouble("feelslike_c");

        JSONObject condition = current.getJSONObject("condition");
        String conditionText = condition.getString("text");

        return new Weather(localtime, temperature, feelsLikeTemperature, conditionText);
    }

    private List<Weather> getWeatherFromDay(JSONObject day) {
        JSONArray hours = day.getJSONArray("hour");
        List<Weather> weathers = new ArrayList<>();
        hours.forEach((hour) -> {
            weathers.add(getWeatherFromHour((JSONObject) hour));
        });
        return weathers;
    }

    private Weather getWeatherFromHour(JSONObject hour) {
        String time = hour.getString("time");
        double temperature = hour.getDouble("temp_c");
        double feelsLikeTemperature = hour.getDouble("feelslike_c");

        JSONObject condition = hour.getJSONObject("condition");
        String conditionText = condition.getString("text");

        return new Weather(time, temperature, feelsLikeTemperature, conditionText);
    }
}
