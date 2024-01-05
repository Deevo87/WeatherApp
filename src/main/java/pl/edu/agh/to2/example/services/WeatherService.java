package pl.edu.agh.to2.example.services;

import org.springframework.stereotype.Service;
import pl.edu.agh.to2.example.ConnectionData;
import pl.edu.agh.to2.example.DTOs.ForecastWeatherDTO;
import pl.edu.agh.to2.example.DTOs.ForecastWeatherDTOMapper;
import pl.edu.agh.to2.example.DTOs.WeatherDTO;
import pl.edu.agh.to2.example.DTOs.WeatherDTOMapper;
import pl.edu.agh.to2.example.exceptions.*;
import pl.edu.agh.to2.example.model.Weather;
import pl.edu.agh.to2.example.services.Parser;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static java.lang.Math.min;
import static java.lang.Math.max;
import static pl.edu.agh.to2.example.classifiers.RainClassifier.compareRainIntensity;
import static pl.edu.agh.to2.example.classifiers.SnowClassifier.compareSnowIntensity;
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

    private final List<List<Integer>> combinedHoursIntervals = new ArrayList<>() {{
        add(Arrays.asList(0, 5));
        add(Arrays.asList(6, 11));
        add(Arrays.asList(12, 17));
        add(Arrays.asList(18, 23));
    }};

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

    private boolean getHistoryWeather(String city) throws Exception {
        LocalDate currentDate = LocalDate.now();
        LocalDate yesterdayDate = currentDate.minusDays(1);
        LocalDate beforeYesterdayDate = currentDate.minusDays(1);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String yesterday = yesterdayDate.format(formatter);
        String beforeYesterday = beforeYesterdayDate.format(formatter);

        String endpoint = "/history.json";
        String urlYesterday = this.basicUrl + endpoint + "?key=" + this.apiKey + "&q=" + city + "&dt=" + yesterday;
        String urlBeforeYesterday = this.basicUrl + endpoint + "?key=" + this.apiKey + "&q=" + city + "&dt=" + beforeYesterday;

        boolean isMudFromYesterday = (this.parser.parseMud(getWeatherResponse(urlYesterday)) != 0);
        boolean isMudFromBeforeYesterday = (this.parser.parseMud(getWeatherResponse(urlBeforeYesterday)) != 0);

        return (isMudFromYesterday || isMudFromBeforeYesterday);
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

    private List<Weather> makeHourInterval(List<Weather> startLoc, List<Weather> endLoc, int start, int end) {
        List<Weather> result = new ArrayList<>();

        result.addAll(
                startLoc.subList(start, end)
        );
        result.addAll(
                endLoc.subList(start, end)
        );

        return result;
    }

    private ForecastWeatherDTO combineWeathers(List<Weather> weathers, String mud) throws RainClassifyingException, SnowClassifyingException, WindClassifyingException, TemperatureException {
        String worstRain = weathers.get(3).getRainStrength();
        double lowestFeelsLikeTemp = weathers.get(3).getFeelsLikeTemperature();
        double lowestTemp = weathers.get(3).getTemperatureDouble();
        String worstSnow = weathers.get(3).getSnowStrength();
        double worstWind = weathers.get(3).getWindVelocityInKph();

        for (Weather weather : weathers) {
            worstRain = switch(compareRainIntensity(worstRain, weather.getRainStrength())) {
                case 1 -> worstRain;
                default -> weather.getRainStrength();
            };
            worstSnow = switch(compareSnowIntensity(worstSnow, weather.getSnowStrength())) {
                case 1 -> worstSnow;
                default -> weather.getSnowStrength();
            };
            worstWind = max(worstWind, weather.getWindVelocityInKph());
            lowestTemp = min(lowestTemp, weather.getTemperatureDouble());
            lowestFeelsLikeTemp = min(lowestFeelsLikeTemp, weather.getFeelsLikeTemperature());
        }
        return forecastWeatherDTOMapper.createForecastWeatherResponse(
                weathers.get(3).getDate(),
                classifyTemperature(lowestTemp),
                lowestFeelsLikeTemp,
                classifyWind(worstWind),
                worstRain,
                worstSnow,
                mud
        );
    }

    public List<List<ForecastWeatherDTO>> getTripConditions(String startLocation, String destinationLocation, int days) throws Exception {

        boolean isMud = getHistoryWeather(startLocation) || getHistoryWeather(destinationLocation);
        String val = new String();
        if (isMud) {
            val = "Yes";
        }
        else {
            val = "No";
        }

        List<List<Weather>> forecastWeatherForStartLoc = getForecastWeather(startLocation, days);
        List<List<Weather>> forecastWeatherForDestLoc = getForecastWeather(destinationLocation, days);
        List<List<ForecastWeatherDTO>> tripForecast = new ArrayList<>();
        for (int i = 0; i < forecastWeatherForDestLoc.size(); i++) {
            List<ForecastWeatherDTO> day = new ArrayList<>();

            for (List<Integer> interval: combinedHoursIntervals) {
                List<Weather> combinedHours;
                combinedHours = makeHourInterval(
                        forecastWeatherForStartLoc.get(i),
                        forecastWeatherForDestLoc.get(i),
                        interval.get(0),
                        interval.get(1)
                );
                day.add(combineWeathers(combinedHours, val));
            }
            tripForecast.add(day);
        }
        System.out.println(
                tripForecast
        );
        return tripForecast;
    }
}