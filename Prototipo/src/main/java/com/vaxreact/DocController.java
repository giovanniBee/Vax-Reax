package com.vaxreact;

import com.jfoenix.controls.JFXButton;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DocController implements Initializable{

    @FXML
    private Pane reportView;

    @FXML
    private Pane homePage;

    @FXML
    private Pane patientsView;

    @FXML
    private AnchorPane stage;

    @FXML
    private ImageView exit;

    @FXML
    private Label menu;

    @FXML
    private Label menuBack;

    @FXML
    private AnchorPane slider;

    @FXML
    private TableView patientTable;

    @FXML
    private TableView<DocModel> patientIdTable;

    @FXML
    private TableView<DocModel> riskFactorTable;

    @FXML
    private TableView<DocModel> adReactionTable;

    @FXML
    private TableColumn<DocModel, String> patientIdColumn;

    @FXML
    private TableColumn<DocModel, String> adReactionColumn;

    @FXML
    private TableColumn<DocModel, Date> dateReactionColumn;

    @FXML
    private TableColumn<DocModel, String> riskFactorColumn;

    @FXML
    private TextField patientIdTextField;

    @FXML
    private TextField adReactionTextField;

    @FXML
    private TextField riskFactorTextField;

    /*
    @FXML
    private JFXButton reportButton;

    @FXML
    private JFXButton reportHighButton;

    @FXML
    private JFXButton viewPatientsButton;

    @FXML
    private JFXButton viewPatientsHighButton;
*/
    ObservableList<DocModel> docModelObservableList = FXCollections.observableArrayList();

    public  void initialize (URL url, ResourceBundle resourse){
        reportView.setVisible(false);
        patientsView.setVisible(false);
        homePage.setVisible(true);
        slider.setTranslateX(0); //se inizializzata a -176 il menu non compare

        DataBaseConnection connetNow = new DataBaseConnection();
        Connection connectDB = connetNow.getConnection();

        String patienIdQuery = "";

        try{
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(patientIdQuery);

        }catch (SQLException e){
            Logger.getLogger(DocController.class.getName()).log(Level.SEVERE,null, e);
            e.printStackTrace();
        }

        exit.setOnMouseClicked(event -> {
            System.exit(0);
        });

        menu.setOnMouseClicked(event -> {

            TranslateTransition slideMenu = new TranslateTransition();
            TranslateTransition slidePatientTable = new TranslateTransition();
            TranslateTransition slideReportTable = new TranslateTransition();

            slideMenu.setDuration(Duration.seconds(0.5));
            slidePatientTable.setDuration(Duration.seconds(0.5));
            slideReportTable.setDuration(Duration.seconds(0.5));

            slideMenu.setNode(slider);
            slidePatientTable.setNode(patientTable);
            slideReportTable.setNode(patientIdTable);

            slideMenu.setToX(0);
            slidePatientTable.setToX(0);
            slideReportTable.setToX(0);

            slideMenu.play();
            slidePatientTable.play();
            slideReportTable.play();

            slider.setTranslateX(-176);

            slideMenu.setOnFinished((ActionEvent e) -> {

                patientTable.prefWidthProperty().bind(patientsView.widthProperty());
                patientIdTable.prefWidthProperty().bind(patientsView.widthProperty());

                menu.setVisible(false);
                menuBack.setVisible(true);
            });
        });



        menuBack.setOnMouseClicked(event -> {
           // final Bounds layoutBounds = patientTable.getLayoutBounds();
           // System.out.println(layoutBounds);
            TranslateTransition slideMenu = new TranslateTransition();
            TranslateTransition slidePatientTable = new TranslateTransition();
            TranslateTransition slideReportTable = new TranslateTransition();

            slideMenu.setDuration(Duration.seconds(0.5));
            slidePatientTable.setDuration(Duration.seconds(0.5));
            slideReportTable.setDuration(Duration.seconds(0.5));

            slideMenu.setNode(slider);
            slidePatientTable.setNode(patientTable);
            slideReportTable.setNode(patientIdTable);

            slideMenu.setToX(-176);
            slidePatientTable.setToX(-176);
            slideReportTable.setToX(-176);

            slideMenu.play();
            slidePatientTable.play();
            slideReportTable.play();
            slider.setTranslateX(0);

            patientTable.prefWidthProperty().bind(stage.widthProperty());
            patientIdTable.prefWidthProperty().bind(stage.widthProperty());

            slideMenu.setOnFinished((ActionEvent e) -> {
                menu.setVisible(true);
                menuBack.setVisible(false);
            });
        });

    }

    public void viewPatientsButtonOnAction(ActionEvent actionEvent) {
        homePage.setVisible(false);
        reportView.setVisible(false);
        patientsView.setVisible(true);
    }

    public void reportButtonOnAction(ActionEvent actionEvent) {
        homePage.setVisible(false);
        patientsView.setVisible(false);
        reportView.setVisible(true);
    }

    public void viewPatientsHighButtonOnActon(ActionEvent actionEvent) {
        homePage.setVisible(false);
        reportView.setVisible(false);
        patientsView.setVisible(true);
    }

    public void reportHighButtonOnActon(ActionEvent actionEvent) {
        homePage.setVisible(false);
        patientsView.setVisible(false);
        reportView.setVisible(true);
    }
}