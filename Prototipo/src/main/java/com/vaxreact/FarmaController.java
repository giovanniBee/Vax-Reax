package com.vaxreact;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;


public class FarmaController implements Initializable {
/*
    private JFXButton basicAnalysisHighButton;
    private JFXButton reportsHighButton;
    private JFXButton basicAnalysisButton;
    private JFXButton reportsButton;
    private JFXButton controlPhaseButton;
*/
    @FXML
    private Pane homePage;
    @FXML
    private Pane reportsView;
    @FXML
    private Pane controlPhaseView;
    @FXML
    private Pane basicAnalysisView;

    private String reportMessage;

    public void setReportMessage(String nodeMessage){
        this.reportMessage = nodeMessage;
    }
    public String getReportMessage(){
        return  this.reportMessage;
    }
    public void displayMessage(){
        System.out.println(getReportMessage());
    }

    public void initialize(URL url, ResourceBundle rs){

        setHomePageView();
        Platform.runLater(() ->{
            displayMessage();
        });

    }

    @FXML
    private void basicAnalysisHighButtonOnActon(ActionEvent actionEvent) {
        setBasicAnalysisView();
    }
    @FXML
    private void reportsHighButtonOnActon(ActionEvent actionEvent) {
        setReportsView();
    }
    @FXML
    private void basicAnalysisButtonOnAction(ActionEvent actionEvent) {
        setBasicAnalysisView();
    }
    @FXML
    private void reportsButtonOnAction(ActionEvent actionEvent) {
        setReportsView();
    }
    @FXML
    private void controlPhaseOnAction(ActionEvent actionEvent) {
        setControlPhaseView();
    }

    //Il controller del farmacologo alterna le varie opzioni di vista
    public void setHomePageView(){
        controlPhaseView.setVisible(false);
        basicAnalysisView.setVisible(false);
        reportsView.setVisible(false);
        homePage.setVisible(true);
    }
    public void setReportsView(){
        homePage.setVisible(false);
        controlPhaseView.setVisible(false);
        basicAnalysisView.setVisible(false);
        reportsView.setVisible(true);
    }
    public  void setBasicAnalysisView(){
        homePage.setVisible(false);
        reportsView.setVisible(false);
        controlPhaseView.setVisible(false);
        basicAnalysisView.setVisible(true);
    }
    public  void setControlPhaseView(){
        homePage.setVisible(false);
        reportsView.setVisible(false);
        controlPhaseView.setVisible(false);
        controlPhaseView.setVisible(true);
    }
}
