package pl.edu.agh.to2.example;

import javafx.application.Application;
import pl.edu.agh.to2.example.services.WeatherService;

import java.util.logging.Logger;

public class Main {

	private static final Logger log = Logger.getLogger(Main.class.toString());

	public static void main(String[] args) throws Exception {
		log.info("Hello world");

		Application.launch(WeatherApp.class);
	}
}
