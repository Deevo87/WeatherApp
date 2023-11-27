package Gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application  {
    @Override
    public void start(Stage primaryStage) throws IOException {

        FXMLLoader root = new FXMLLoader(getClass().getResource("/form.fxml"));
        Scene scene = new Scene(root.load(), 618, 300);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
