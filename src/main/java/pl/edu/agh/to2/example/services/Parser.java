package pl.edu.agh.to2.example.services;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import pl.edu.agh.to2.example.exceptions.RainClassifyingException;
import pl.edu.agh.to2.example.exceptions.SnowClassifyingException;
import pl.edu.agh.to2.example.exceptions.TemperatureException;
import pl.edu.agh.to2.example.exceptions.WindClassifyingException;
import pl.edu.agh.to2.example.model.Weather;

import java.util.ArrayList;
import java.util.List;

@Service
public class Parser {

    public List<List<Weather>> parseForecastWeather(String response) {
        List<List<Weather>> weathersForEachDay = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(response);
        JSONObject forecast = jsonObject.getJSONObject("forecast");
        JSONArray forecastDaysArray = forecast.getJSONArray("forecastday");

        forecastDaysArray.forEach(day ->
                weathersForEachDay.add(getWeatherFromDay((JSONObject) day))
        );
        return weathersForEachDay;
    }

    public int parseMud(String response) {
        JSONObject jsonObject = new JSONObject(response);
        JSONObject forecast = jsonObject.getJSONObject("forecast");
        JSONArray forecastDay = forecast.getJSONArray("forecastday");
        JSONObject firstDay = forecastDay.getJSONObject(0);
        JSONObject dayObject = firstDay.getJSONObject("day");
        return dayObject.getInt("daily_will_it_rain");
    }

    public Weather parseCurrentWeather(String response) {
        JSONObject jsonObject = new JSONObject(response);

        JSONObject location = jsonObject.getJSONObject("location");
        String localtime = location.getString("localtime");

        JSONObject current = jsonObject.getJSONObject("current");
        double temperature = current.getDouble("temp_c");

        double windVelocityInKph = current.getDouble("wind_kph");

        double precipitationAmount = current.getDouble("precip_mm");

        JSONObject condition = current.getJSONObject("condition");
        String conditionText = condition.getString("text");
        return new Weather(localtime, temperature , conditionText, windVelocityInKph, precipitationAmount);
    }

    private List<Weather> getWeatherFromDay(JSONObject day) {
        JSONArray hours = day.getJSONArray("hour");
        List<Weather> weathers = new ArrayList<>();
        hours.forEach(hour -> {
            try {
                weathers.add(getWeatherFromHour((JSONObject) hour));
            } catch (WindClassifyingException | TemperatureException | RainClassifyingException |
                     SnowClassifyingException e) {
                throw new RuntimeException(e);
            }
        });
        return weathers;
    }

    private Weather getWeatherFromHour(JSONObject hour) throws WindClassifyingException, TemperatureException, RainClassifyingException, SnowClassifyingException {
        String time = hour.getString("time");
        double temperature = hour.getDouble("temp_c");

        double windVelocityInKph = hour.getDouble("wind_kph");
        double precipitationAmount = hour.getDouble("precip_mm");
        JSONObject condition = hour.getJSONObject("condition");
        String conditionText = condition.getString("text");

        return new Weather(time, temperature, conditionText, windVelocityInKph, precipitationAmount);
    }
}