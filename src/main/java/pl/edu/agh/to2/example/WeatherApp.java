package pl.edu.agh.to2.example;

import javafx.application.Application;

import java.util.logging.Logger;

public class WeatherApp {

	private static final Logger log = Logger.getLogger(WeatherApp.class.toString());

	public static void main(String[] args) throws Exception {
		log.info("Hello world");
		WeatherService weatherService = new WeatherService();
		weatherService.getCurrentWeather("Cracow");
		Application.launch(App.class);
	}
}
