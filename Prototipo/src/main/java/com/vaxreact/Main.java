package com.vaxreact;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Properties;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //esegue un controllo sull'invio delle notifiche del sabato pomeriggio
        runWarningTask();

        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        stage.initStyle(StageStyle.UNDECORATED);
       // stage.setTitle("Login page");
        Scene scene = new Scene(root,520,400);
        scene.getStylesheets().add(getClass().getResource("stylesheet.css").toExternalForm());
        stage.setScene(scene);

        
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public void runWarningTask() {

        Calendar calendar = Calendar.getInstance();
        calendar.set(
                Calendar.DAY_OF_WEEK,
                Calendar.SATURDAY
        );
        calendar.set(Calendar.HOUR_OF_DAY, 16);
        calendar.set(Calendar.MINUTE, 30);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);



        Timer time = new Timer(); // Instantiate Timer Object

        // Start running the task on Monday at 15:40:00, period is set to 8 hours
        // if you want to run the task immediately, set the 2nd parameter to 0
        time.schedule(new WarningTask(), calendar.getTime(), TimeUnit.HOURS.toMillis(1));
    }
}