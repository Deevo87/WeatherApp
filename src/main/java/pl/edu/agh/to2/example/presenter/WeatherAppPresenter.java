package pl.edu.agh.to2.example.presenter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import pl.edu.agh.to2.example.model.Weather;
import pl.edu.agh.to2.example.services.WeatherService;

public class WeatherAppPresenter {
    @FXML
    private TextField cityInputField;
    @FXML
    private Text currentCityField;
    @FXML
    private Text dateField;
    @FXML
    private Text temperatureField;
    @FXML
    private Text feelsLikeTemperatureField;
    @FXML
    private Text conditionField;

    private final WeatherService weatherService;

    public WeatherAppPresenter(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @FXML
    private void handleCheckWeatherAction(ActionEvent event){
        updateCurrentWeather();
    }

    private void updateCurrentWeather(){
        Weather currentWeather;
        String city = cityInputField.getText();

        try {
            currentWeather = weatherService.getCurrentWeather(city);
            dateField.setText(currentWeather.getDate().toLocalDate() + " " + currentWeather.getDate().toLocalTime());
            temperatureField.setText(currentWeather.getTemperature() + " \u00B0C");
            feelsLikeTemperatureField.setText(currentWeather.getFeelsLikeTemperature()  + " \u00B0C");
            conditionField.setText(currentWeather.getCondition());

            currentCityField.setText(city);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}