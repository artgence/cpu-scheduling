package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/home/circleci/project/src/main/resources/CPU Scheduling.fxml"));
            Scene scene = new Scene(root,1254,758);
            scene.getStylesheets().add(getClass().getResource("/home/circleci/project/src/main/resources/application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("CPU Scheduling");
            Image Icon = new Image("file:CPU.png");
            primaryStage.getIcons().add(Icon);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
