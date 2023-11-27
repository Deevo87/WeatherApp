package pl.edu.agh.to2.example;

import javafx.application.Application;

import java.util.logging.Logger;

public class WeatherApp {

	private static final Logger log = Logger.getLogger(WeatherApp.class.toString());

	public static void main(String[] args) throws Exception {
		log.info("Hello world");
		WeatherService weatherService = new WeatherService();
		System.out.println(weatherService.getCurrentWeather("Cracow"));
		System.out.println(weatherService.getForecastWeather("Cracow", 2));

		Application.launch(App.class);
	}
}
