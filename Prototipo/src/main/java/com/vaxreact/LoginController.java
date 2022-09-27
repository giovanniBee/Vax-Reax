package com.vaxreact;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.*;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;
import java.util.Properties;
import java.util.ResourceBundle;

import static com.vaxreact.Query.preparedLogin;

public class LoginController implements Initializable {
    @FXML
    private Button cancelButton;
    @FXML
    private Label enterMessageLabel;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordPasswordField;
    @FXML
    private CheckBox saveCredentialsCheckBox;



    private boolean accessFlag = false;

    public void cancelButtonOnAction(){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();

    }

    public void enterButtonOnAction() throws NoSuchAlgorithmException {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        //Inserisco i dati inseriti in input dell'utente che prova a fare il login nel modello dove risiedono i dati utente
        UserModel user = retriveUserInfo(usernameTextField.getText(),passwordPasswordField.getText());

        if (!user.getUser().isBlank() && !user.getPassword().isBlank()) {
            //enterMessageLabel.setText("Welcome!");
            validateLogin(user);
            if(accessFlag)
                stage.close();
        } else {
            enterMessageLabel.setText("Please enter username and password");
        }
    }

    public void validateLogin(UserModel user) throws NoSuchAlgorithmException {

        String salt = Encrypt.findSalt(user);
        DataBaseConnection connectNow = new DataBaseConnection();
        Connection connectDB = connectNow.getConnection();

        if(!salt.equals("")) {
            String hashPassword=Encrypt.toHexString(Encrypt.getSHA(user, salt));

            try {

                //prepared statement for login
                PreparedStatement statement = connectDB.prepareStatement(preparedLogin());
                statement.setString(1, user.getUser());
                statement.setString(2, hashPassword);
                ResultSet queryResult = statement.executeQuery();

                while (queryResult.next()) {
                    if (queryResult.getInt(1) == 1) {
                        accessFarmaDb(user);
                        accessFlag = !accessFlag;
                    } else if (queryResult.getInt(1) == 0) {
                        accessDoctorDb(user);
                        accessFlag = !accessFlag;
                    }
                }
                //save users credentials if checkbox is selected
                if(saveCredentialsCheckBox.isSelected())
                    saveUser(user);
                else
                    clearCredentials();
               //reset camps on failed login
                enterMessageLabel.setText("Invalidate login. Please try again.");
                usernameTextField.setText("");
                passwordPasswordField.setText("");

            } catch (Exception e) {
                e.printStackTrace();

            }
        }else{
            enterMessageLabel.setText("Invalidate login. Please try again.");
        }
    }

    public void accessDoctorDb(UserModel user){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(LoginController.class.getResource("DoctorView.fxml"));
        try{
            Parent root = loader.load();
            DocController dc = loader.getController();
            dc.setUserInfo(user);
            Stage docStage = new Stage();
            Scene scene = new Scene(root,800,500);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("docstylesheet.css")).toExternalForm());
            docStage.setScene(scene);

            docStage.show();
        } catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }

    }

    public void accessFarmaDb(UserModel user){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(LoginController.class.getResource("FarmaView.fxml"));

        try{
            Parent root = loader.load();
            FarmaController fc = loader.getController();
            fc.setUserInfo(user);
            Stage farmaStage = new Stage();
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

    public void saveUser(UserModel user){
        try (OutputStream output = new FileOutputStream("config.properties")) {

            Properties credentials = new Properties();

            // set the properties value
            credentials.setProperty("User", user.getUser());
            credentials.setProperty("Password", user.getPassword());

            // save properties to project root folder
            credentials.store(output, null);


        } catch (IOException io) {
            io.printStackTrace();
        }
    }
    private void clearCredentials() {
        try (OutputStream output = new FileOutputStream("config.properties")) {

            Properties credentials = new Properties();

            // set the properties value
            credentials.setProperty("User", "");
            credentials.setProperty("Password", "");

            // save properties to project root folder
            credentials.store(output, null);


        } catch (IOException io) {
            io.printStackTrace();
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try (InputStream input = new FileInputStream("config.properties")) {
            Properties credentials = new Properties();
            // load credentials file
            credentials.load(input);
            // get the credentials values and put in the labels
            usernameTextField.setText(credentials.getProperty("User"));
            passwordPasswordField.setText(credentials.getProperty("Password"));

            //se c'erano dati di accesso salvati, la checkbox è inizializzata a true, cosi da non doverla ripremere
            saveCredentialsCheckBox.setSelected(!usernameTextField.getText().equals(""));

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}