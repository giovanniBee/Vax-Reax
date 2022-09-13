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
import java.util.Calendar;

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

        //Inserisco i dati inseriti in input dell'utente che prova a fare il login nel modello dove risiedono i dati utente
        UserModel user = retriveUserInfo(usernameTextField.getText(),passwordPasswordField.getText());

        if (!user.getUser().isBlank() && !user.getPassword().isBlank()) {
            //enterMessageLabel.setText("Welcome!");
            validateLogin(user);
        } else {
            enterMessageLabel.setText("Please enter username and password");
        }
    }

    public void validateLogin(UserModel user){

        DataBaseConnection connectNow = new DataBaseConnection();
        Connection connectDB = connectNow.getConnection();

        String verifyLoginDoctor = "SELECT COUNT(1) FROM \"Doctor\".\"doctorList\" WHERE id = '" + user.getUser() + "' AND password = '" + user.getPassword() + "'";
        String verifyLoginFarma = "SELECT COUNT(1) FROM \"Doctor\".\"farmaList\" WHERE id = '" + user.getUser() + "' AND password = '" + user.getPassword() + "'";

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
                    accessDoctorDb(user);
                }
                else{
                    enterMessageLabel.setText("Invalidate login. Please try again.");
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void accessDoctorDb(UserModel user){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(LoginController.class.getResource("DoctorView.fxml"));
        try{
            Parent root = (Parent) loader.load();
            //DocController dc = loader.getController();
            //dc.setUserInfo(user);
            Stage docStage = new Stage();
            //docStage.initStyle(StageStyle.UNDECORATED);
            docStage.setScene(new Scene(root,800,500));
            docStage.show();
        } catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }

    }

    public void accessFarmaDb(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(LoginController.class.getResource("FarmaView.fxml"));

        try{
            Parent root = (Parent) loader.load();
            FarmaController fc = loader.getController();
            fc.setReportMessage("Hello farmacologist!");
            Stage farmaStage = new Stage();
            //farmaStage.initStyle(StageStyle.UNDECORATED);
            farmaStage.setScene(new Scene(root,800,500));
            farmaStage.show();
        } catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public UserModel retriveUserInfo(String user, String password){
        UserModel userModel = new UserModel();
        userModel.setUser(user);
        userModel.setPassword(password);
        return userModel;
    }

}