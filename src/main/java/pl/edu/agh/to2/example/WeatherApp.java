package pl.edu.agh.to2.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class WeatherApp extends Application  {
    @Override
    public void start(Stage primaryStage) throws IOException {

        FXMLLoader root = new FXMLLoader(getClass().getResource("/view/form.fxml"));
        Scene scene = new Scene(root.load(), 618, 300);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
