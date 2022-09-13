package com.vaxreact;

import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WarningTask extends TimerTask {

    public void run(){
        try {
            sendWarning();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void sendWarning(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("FarmaView.fxml")); // [b]<== path to .fxml is correct[/b]
        try {
            loader.load();  // [b]<=== ERROR here! The path in getResource() is correct[/b]
        } catch (IOException e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("Loader error : " + e);
        }
        String nodeMessage = "Sono arrivate più di 50 segnalazioni questa settimana!";
        FarmaController farmaController = loader.getController();
        farmaController.setReportMessage(nodeMessage);
    }
}
