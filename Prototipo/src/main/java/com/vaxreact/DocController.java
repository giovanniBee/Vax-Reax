package com.vaxreact;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.postgresql.util.PSQLException;

public class DocController implements Initializable{

    public static String clickedPatientId;
    public static String clickedAdReactionDate;

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
    private TableView<DocPatientViewTableModel>patientViewTable;

    @FXML
    private TableView<DocPatientIDTableModel> patientIdTable;

    @FXML
    private TableView<DocRiskFactorTableModel> riskFactorTable;

    @FXML
    private TableView<DocAdverseReactionTableModel> adverseReactionTable;

    @FXML
    private TableColumn<DocPatientIDTableModel, String> patientIdColumn;

    @FXML
    private TableColumn<DocAdverseReactionTableModel, String> adverseReactionColumn;

    @FXML
    private TableColumn<DocRiskFactorTableModel, String> riskFactorColumn;

    @FXML
    private TableColumn<DocPatientViewTableModel, String> adverseReactionDateColumn;

    @FXML
    private TableColumn<DocPatientViewTableModel, String> adverseReactionPatientViewColumn;

    @FXML
    private TableColumn<DocPatientViewTableModel, String> patientIdPatientViewColumn;

    @FXML
    private TableColumn<DocPatientViewTableModel, String> reportDateColumn;

    @FXML
    private TableColumn<DocPatientViewTableModel, String> reportIdColumn;

    @FXML
    private TextField patientIdTextField;

    @FXML
    private TextField adReactionTextField;

    @FXML
    private TextField riskFactorTextField;

    @FXML
    private TextField patientViewTextField;

    @FXML
    private Text commitAdverseReaction;

    @FXML
    private Text commitPatientID;

    @FXML
    private Text commitRiskFactor;

    @FXML
    private TextField commitAdReactionDate;

    @FXML
    private Label checkCommitLabel;

    /*
    @FXML
    private Button sendReportButton;

    @FXML
    private JFXButton reportButton;

    @FXML
    private JFXButton reportHighButton;

    @FXML
    private JFXButton viewPatientsButton;

    @FXML
    private JFXButton viewPatientsHighButton;
*/
    ObservableList<DocPatientIDTableModel> docPatientIDTableModelObservableList = FXCollections.observableArrayList();
    ObservableList<DocRiskFactorTableModel> docRiskFactorTableModelObservableList = FXCollections.observableArrayList();
    ObservableList<DocAdverseReactionTableModel>docAdverseReactionTableModelObservableList = FXCollections.observableArrayList();
    ObservableList<DocPatientViewTableModel>docPatientViewTableModelObservableList = FXCollections.observableArrayList();

    public  void initialize (URL url, ResourceBundle resourse){


        reportView.setVisible(false);
        patientsView.setVisible(false);
        homePage.setVisible(true);
        slider.setTranslateX(0); //se inizializzata a -176 il menu non compare

        initializeTables();
        updatePatientView();

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
            slidePatientTable.setNode(patientViewTable);
            slideReportTable.setNode(patientIdTable);

            slideMenu.setToX(0);
            slidePatientTable.setToX(0);
            slideReportTable.setToX(0);

            slideMenu.play();
            slidePatientTable.play();
            slideReportTable.play();

            slider.setTranslateX(-176);

            slideMenu.setOnFinished((ActionEvent e) -> {

                patientViewTable.prefWidthProperty().bind(patientsView.widthProperty());
                patientIdTable.prefWidthProperty().bind(patientsView.widthProperty());

                menu.setVisible(false);
                menuBack.setVisible(true);
            });
        });

        menuBack.setOnMouseClicked(event -> {

            TranslateTransition slideMenu = new TranslateTransition();
            TranslateTransition slidePatientTable = new TranslateTransition();
            TranslateTransition slideReportTable = new TranslateTransition();

            slideMenu.setDuration(Duration.seconds(0.5));
            slidePatientTable.setDuration(Duration.seconds(0.5));
            slideReportTable.setDuration(Duration.seconds(0.5));

            slideMenu.setNode(slider);
            slidePatientTable.setNode(patientViewTable);
            slideReportTable.setNode(patientIdTable);

            slideMenu.setToX(-176);
            slidePatientTable.setToX(-176);
            slideReportTable.setToX(-176);

            slideMenu.play();
            slidePatientTable.play();
            slideReportTable.play();
            slider.setTranslateX(0);

            patientViewTable.prefWidthProperty().bind(stage.widthProperty());
            patientIdTable.prefWidthProperty().bind(stage.widthProperty());

            slideMenu.setOnFinished((ActionEvent e) -> {
                menu.setVisible(true);
                menuBack.setVisible(false);
            });
        });

        patientIdTable.setOnMousePressed(event -> {
            String clicked = patientIdTable.getSelectionModel().getSelectedItem().getPatientID();
            commitPatientID.setText(clicked);
            System.out.println(clicked);
        });

        riskFactorTable.setOnMousePressed(event -> {
            String clicked = riskFactorTable.getSelectionModel().getSelectedItem().getRiskFactor();
            commitRiskFactor.setText(clicked);
            System.out.println(clicked);
        });
        adverseReactionTable.setOnMousePressed(event -> {
            String clicked = adverseReactionTable.getSelectionModel().getSelectedItem().getAdverseReaction();
            commitAdverseReaction.setText(clicked);
            System.out.println(clicked);
        });

        patientViewTable.setOnMousePressed(event -> {
            clickedPatientId = patientViewTable.getSelectionModel().getSelectedItem().getPatientId();
            clickedAdReactionDate = patientViewTable.getSelectionModel().getSelectedItem().getAdReactionDate();

            //System.out.println(clickedAdReactionDate + getClickedAdReactionDate());

            //setClickedPatient(clickedPatientId);
            //setClickedAdReactionDate(clickedAdReactionDate);
            //DocController docController = new DocController();
            //docController.setClickedPatient(clickedPatientId);
            //System.out.println("Doc controller: " + docController.getClickedPatient());
            //Apertura view vaccini
            try{
                Parent root = FXMLLoader.load(getClass().getResource("VaccineView.fxml"));
                Stage docStage = new Stage();
                //docStage.initStyle(StageStyle.UNDECORATED);
                docStage.setScene(new Scene(root,800,500));
                docStage.show();

                /*
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("VaccineView.fxml"));
                Parent root = (Parent)fxmlLoader.load();
                VaccineController controller = fxmlLoader.getController();
                //controller.setPatient(clickedPatientId);
                controller.setPatient(clickedPatientId);
                //controller.setDate(clickedAdReactionDate);
                controller.setDate(clickedPatientId);
               // System.out.println(clickedAdReactionDate + " " + clickedPatientId);
                Stage docStage = new Stage();
                Scene scene = new Scene(root);
                docStage.setScene(scene);
                docStage.show();
                */

            }catch (IllegalStateException exception){
             System.out.println(exception.getMessage());
             System.out.println(exception.getCause());
            }catch (Exception e){
                e.printStackTrace();
                e.getCause();
            }
        });


    }
    public void modificaValue(VaccineController v){

        System.out.println();
        //v.setP(d);
    }

    public DocController(){
        this.clickedPatientId = "";
        this.clickedAdReactionDate = "";
    }
    public void setClickedPatient(String clickedPatientId) {
        this.clickedPatientId = clickedPatientId;
    }

    public void setClickedAdReactionDate(String clickedAdReactionDate) {
        this.clickedAdReactionDate = clickedAdReactionDate;
    }

    public  String getClickedPatient(){
        return this.clickedPatientId;
    }

    public String getClickedAdReactionDate() {
        return this.clickedAdReactionDate;
    }


    public void display(){
        System.out.println(clickedPatientId);
        System.out.println(clickedAdReactionDate);

    }

    public void initializeTables(){
        DataBaseConnection connetNow = new DataBaseConnection();
        Connection connectDB = connetNow.getConnection();

        //Query SQL eseguita su postgreSQL
        String patientIdQuery = "SELECT * FROM \"Doctor\".\"patientList\" ORDER BY id ASC";
        String riskFactorQuery = "SELECT * FROM \"Doctor\".\"FattoriRischio\" ORDER BY nome ASC";
        String adverseReactionQuery = "SELECT * FROM \"Doctor\".\"reazioniAvverse\" ORDER BY nome ASC";

        try{
            Statement patientStatement = connectDB.createStatement();
            Statement riskFactorStatement = connectDB.createStatement();
            Statement adverseReactionStatement = connectDB.createStatement();

            ResultSet patientQueryOutput = patientStatement.executeQuery(patientIdQuery);
            ResultSet riskFactorQueryOutput = riskFactorStatement.executeQuery(riskFactorQuery);
            ResultSet adverseReactionQueryOutput = adverseReactionStatement.executeQuery(adverseReactionQuery);

            while(patientQueryOutput.next()){
                //il parametro di getString è una stringa che riporta il nome esatto della colonna all'interno del Database su pSQL
                String queryPatientId = patientQueryOutput.getString("id");
                //Popolazione ObservableList
                docPatientIDTableModelObservableList.add(new DocPatientIDTableModel(queryPatientId));
            }
            while(riskFactorQueryOutput.next()){
                //il parametro di getString è una stringa che riporta il nome esatto della colonna all'interno del Database su pSQL
                String queryRiskFactor = riskFactorQueryOutput.getString("nome");
                //Popolazione ObservableList
                docRiskFactorTableModelObservableList.add(new DocRiskFactorTableModel(queryRiskFactor));
            }
            while(adverseReactionQueryOutput.next()){
                //il parametro di getString è una stringa che riporta il nome esatto della colonna all'interno del Database su pSQL
                String queryAdverseReaction = adverseReactionQueryOutput.getString("nome");
                //Popolazione ObservableList
                docAdverseReactionTableModelObservableList.add(new DocAdverseReactionTableModel((queryAdverseReaction)));
            }

            //PropertyValueFactory corrisponde agli stessi campi della classe ...Model
            patientIdColumn.setCellValueFactory(new PropertyValueFactory<>("patientID"));
            riskFactorColumn.setCellValueFactory(new PropertyValueFactory<>("riskFactor"));
            adverseReactionColumn.setCellValueFactory(new PropertyValueFactory<>("adverseReaction"));

            patientIdTable.setItems(docPatientIDTableModelObservableList);
            riskFactorTable.setItems(docRiskFactorTableModelObservableList);
            adverseReactionTable.setItems(docAdverseReactionTableModelObservableList);

            //Filtraggio dati mentre si cerca nella tabella
            FilteredList<DocPatientIDTableModel> filteredPatientIdTableData = new FilteredList<>(docPatientIDTableModelObservableList, b->true);
            FilteredList<DocRiskFactorTableModel> filteredRiskFactorTableData = new FilteredList<>(docRiskFactorTableModelObservableList, b->true);
            FilteredList<DocAdverseReactionTableModel>filteredAdverseReactionTableData = new FilteredList<>(docAdverseReactionTableModelObservableList, b ->true);

            patientIdTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredPatientIdTableData.setPredicate(DocPatientIDTableModel -> {
                    //SE non si riempie il campo mostra tutti i pazienti
                    if(newValue.isEmpty() || newValue.isBlank() || newValue == null){
                        return true;}
                    String searchKeyword = newValue.toLowerCase();
                    if(DocPatientIDTableModel.getPatientID().toLowerCase().indexOf(searchKeyword) > -1)
                        return true; //C'è un match
                    else
                        return false; //no match
                });
            });
            riskFactorTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredRiskFactorTableData.setPredicate(DocRiskFactorTableModel -> {
                    //SE non si riempie il campo mostra tutti i pazienti
                    if(newValue.isEmpty() || newValue.isBlank() || newValue == null){
                        return true;}
                    String searchKeyword = newValue.toLowerCase();
                    if(DocRiskFactorTableModel.getRiskFactor().toLowerCase().indexOf(searchKeyword) > -1)
                        return true; //C'è un match
                    else
                        return false; //no match
                });
            });
            adReactionTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredAdverseReactionTableData.setPredicate(DocAdverseReactionTableModel -> {
                    //SE non si riempie il campo mostra tutti i pazienti
                    if(newValue.isEmpty() || newValue.isBlank() || newValue == null){
                        return true;}
                    String searchKeyword = newValue.toLowerCase();
                    if(DocAdverseReactionTableModel.getAdverseReaction().toLowerCase().indexOf(searchKeyword) > -1)
                        return true; //C'è un match
                    else
                        return false; //no match
                });
            });

            SortedList<DocPatientIDTableModel> sortedPatientIdTableData = new SortedList<>(filteredPatientIdTableData);
            SortedList<DocRiskFactorTableModel> sortedRiskFactorTableData = new SortedList<>(filteredRiskFactorTableData);
            SortedList<DocAdverseReactionTableModel> sortedAdverseReactionTableData = new SortedList<>(filteredAdverseReactionTableData);

            //bind dei risultati ordinati con la Table View
            sortedPatientIdTableData.comparatorProperty().bind(patientIdTable.comparatorProperty());
            sortedRiskFactorTableData.comparatorProperty().bind(riskFactorTable.comparatorProperty());
            sortedAdverseReactionTableData.comparatorProperty().bind(adverseReactionTable.comparatorProperty());

            //Applico i dati filtrati ed ordinati alla Table View
            patientIdTable.setItems(sortedPatientIdTableData);
            riskFactorTable.setItems(sortedRiskFactorTableData);
            adverseReactionTable.setItems(sortedAdverseReactionTableData);

        }catch (PSQLException e){
            System.out.println(e.getMessage());
        }catch (SQLException e){
            Logger.getLogger(DocController.class.getName()).log(Level.SEVERE,null, e);
            e.printStackTrace();
        }
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

    public void sendReportOnAction(ActionEvent event) {

        if(commitPatientID.getText().isBlank() || commitRiskFactor.getText().isBlank() || commitAdverseReaction.getText().isBlank()){
            checkCommitLabel.setText("Please fill in all fields");
        }
        else {
            DataBaseConnection connetNow = new DataBaseConnection();
            Connection connectDB = connetNow.getConnection();

            String makeReportQuery = "INSERT INTO \"Doctor\".segnalazione(\"reportId\", \"patientId\", \"adverseReaction\", \"adReactionDate\", \"reportDate\") VALUES (DEFAULT, '"+ commitPatientID.getText() +"', '"+ commitAdverseReaction.getText() +"', '"+ commitAdReactionDate.getText()+"', CURRENT_DATE)";

            try {
                Statement reportStatement = connectDB.createStatement();
                int reportQueryOutput = reportStatement.executeUpdate(makeReportQuery);
                updatePatientView();
                checkCommitLabel.setText("");

                }catch (PSQLException e){
                checkCommitLabel.setText("Please enter a correct date (YYYY-MM-DD)");
                Logger.getLogger(DocController.class.getName()).log(Level.FINE,e.getMessage(),e);
                System.out.println(e.getMessage());
                }
                catch (SQLException e){
                Logger.getLogger(DocController.class.getName()).log(Level.SEVERE,null, e);
                e.printStackTrace();
               // System.out.println(e.getMessage());
                }


            commitPatientID.setText("");
            commitRiskFactor.setText("");
            commitAdverseReaction.setText("");
            commitAdReactionDate.setText("");
        }
    }
    public void updatePatientView(){

        docPatientViewTableModelObservableList.clear();

        DataBaseConnection connetNow = new DataBaseConnection();
        Connection connectDB = connetNow.getConnection();
        String patientViewQuery = "SELECT \"reportId\", \"patientId\", \"adverseReaction\", \"adReactionDate\", \"reportDate\" FROM \"Doctor\".\"segnalazione\" ORDER BY \"reportId\" ASC ";
        try {
            Statement patientViewStatement = connectDB.createStatement();
            ResultSet patientViewQueryOutput = patientViewStatement.executeQuery(patientViewQuery);
            while (patientViewQueryOutput.next()) {

                //il parametro di getString è una stringa che riporta il nome esatto della colonna all'interno del Database su pSQL
                String queryReportId = patientViewQueryOutput.getString("reportId");
                String queryPatId = patientViewQueryOutput.getString("patientId");
                String queryAdReaction = patientViewQueryOutput.getString("adverseReaction");
                String queryAdReactionDate = patientViewQueryOutput.getString("adReactionDate");
                String queryReportDate = patientViewQueryOutput.getString("reportDate");

                //Popolazione ObservableList
                docPatientViewTableModelObservableList.add(new DocPatientViewTableModel(queryReportId, queryPatId, queryAdReaction, queryAdReactionDate, queryReportDate));
                reportIdColumn.setCellValueFactory(new PropertyValueFactory<>("reportId"));
                patientIdPatientViewColumn.setCellValueFactory(new PropertyValueFactory<>("patientId"));
                adverseReactionPatientViewColumn.setCellValueFactory(new PropertyValueFactory<>("adverseReaction"));
                adverseReactionDateColumn.setCellValueFactory(new PropertyValueFactory<>("adReactionDate"));
                reportDateColumn.setCellValueFactory(new PropertyValueFactory<>("reportDate"));

                patientViewTable.setItems(docPatientViewTableModelObservableList);

                FilteredList<DocPatientViewTableModel> filteredPatientViewTableData = new FilteredList<>(docPatientViewTableModelObservableList, b -> true);

                patientViewTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                    filteredPatientViewTableData.setPredicate(DocPatientViewTableModel -> {
                        if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                            return true; //C'è un match
                        }
                        String searchKeyword = newValue.toLowerCase();
                        if (DocPatientViewTableModel.getReportId().toLowerCase().indexOf(searchKeyword) > -1) {
                            return true;
                        } else if (DocPatientViewTableModel.getPatientId().toLowerCase().indexOf(searchKeyword) > -1) {
                            return true;

                        } else if (DocPatientViewTableModel.getAdverseReaction().toLowerCase().indexOf(searchKeyword) > -1) {
                            return true;

                        } else if (DocPatientViewTableModel.getAdReactionDate().toLowerCase().indexOf(searchKeyword) > -1) {
                            return true;

                        } else if (DocPatientViewTableModel.getReportDate().toLowerCase().indexOf(searchKeyword) > -1) {
                            return true;

                        } else {
                            return false;//no match found
                        }
                    });
                });
                SortedList<DocPatientViewTableModel> sortedPatientViewTableData = new SortedList<>(filteredPatientViewTableData);
                sortedPatientViewTableData.comparatorProperty().bind(patientViewTable.comparatorProperty());
                patientViewTable.setItems(sortedPatientViewTableData);
            }
            }catch (PSQLException e){
            System.out.println(e.getMessage());
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
}