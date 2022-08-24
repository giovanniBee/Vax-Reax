package com.vaxreact;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.*;

public class LoginController {
    @FXML
    private Button cancelButton;
    @FXML
    private Label enterMessageLabel;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordPasswordField;


    public void cancelButtonOnAction(ActionEvent e){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();

    }

    public void enterButtonOnAction(ActionEvent e) {

        if (!usernameTextField.getText().isBlank() && !passwordPasswordField.getText().isBlank()) {
            //enterMessageLabel.setText("Welcome!");
            validateLogin();
        } else {
            enterMessageLabel.setText("Please enter username and password");
        }
    }

    public void validateLogin(){
        DataBaseConnection connectNow = new DataBaseConnection();
        Connection connectDB = connectNow.getConnection();

        String verifyLoginDoctor = "SELECT COUNT(1) FROM \"Doctor\".\"doctorList\" WHERE id = '" + usernameTextField.getText() + "' AND password = '" + passwordPasswordField.getText() + "'";
        String verifyLoginFarma = "SELECT COUNT(1) FROM \"Doctor\".\"farmaList\" WHERE id = '" + usernameTextField.getText() + "' AND password = '" + passwordPasswordField.getText() + "'";

        try{
            Statement statementDoc = connectDB.createStatement();
            Statement statementFarma = connectDB.createStatement();
            ResultSet docQueryResult = statementDoc.executeQuery(verifyLoginDoctor);
            ResultSet farmaQueryResult = statementFarma.executeQuery(verifyLoginFarma);

            while(docQueryResult.next() == true && farmaQueryResult.next() == true){

                if(farmaQueryResult.getInt(1) == 1){
                    enterMessageLabel.setText("Welcome Farmacologist!");
                    accessFarmaDb();
                }
                else if(docQueryResult.getInt(1) == 1){
                    enterMessageLabel.setText("Welcome Doctor!");
                    accessDoctorDb();
                }
                else{
                    enterMessageLabel.setText("Invalidate login. Please try again.");
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void accessDoctorDb(){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("DoctorView.fxml"));
            Stage docStage = new Stage();
            docStage.initStyle(StageStyle.UNDECORATED);
            docStage.setScene(new Scene(root,520,400));
            docStage.show();
        } catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }

    }

    public void accessFarmaDb(){

        try{
            Parent root = FXMLLoader.load(getClass().getResource("FarmaView.fxml"));
            Stage farmaStage = new Stage();
            farmaStage.initStyle(StageStyle.UNDECORATED);
            farmaStage.setScene(new Scene(root,520,400));
            farmaStage.show();
        } catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

}