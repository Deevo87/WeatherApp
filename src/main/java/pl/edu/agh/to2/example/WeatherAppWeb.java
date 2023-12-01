package pl.edu.agh.to2.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Logger;

@SpringBootApplication
public class WeatherAppWeb {

    private static final Logger log = Logger.getLogger(WeatherAppWeb.class.toString());

    public static void main(String[] args) {
        log.info("Hello world");
        SpringApplication.run(WeatherAppWeb.class);
    }
}
