package pl.edu.agh.to2.example;

public class WeatherAppException extends Exception {

    public WeatherAppException() {
        super();
    }

    public WeatherAppException(String message) {
        super(message);
    }

    public WeatherAppException(String message, Throwable cause) {
        super(message, cause);
    }

    public WeatherAppException(Throwable cause) {
        super(cause);
    }
}
