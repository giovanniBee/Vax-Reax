package com.vaxreact;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
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

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.postgresql.util.PSQLException;


public class DocController implements Initializable{

    public static final  Integer MAX_GRAVITY_LEVEL = 5;

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
    private TableView<ReportsTableModel> reportsTable;

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
    static TableColumn<ReportsTableModel, String> doctorIdColumn;

    @FXML
    static TableColumn<ReportsTableModel, String> adverseReactionDateColumn;

    @FXML
    static TableColumn<ReportsTableModel, String> adverseReactionReportsColumn;

    @FXML
    static TableColumn<ReportsTableModel, String> patientIdReportsColumn;

    @FXML
    static TableColumn<ReportsTableModel, String> reportDateColumn;

    @FXML
    public static TableColumn<ReportsTableModel, String> reportIdColumn;

    @FXML
    private TextField patientIdTextField;

    @FXML
    private TextField adReactionTextField;

    @FXML
    private TextField riskFactorTextField;

    @FXML
    private TextField reportsTextField;

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

    @FXML
    private ChoiceBox<Integer> choiceBox;

    @FXML
    private Button sendReportButton;

    @FXML
    private TextArea descriptionArea;

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
    ObservableList<DocPatientIDTableModel> docPatientIDTableModelObservableList = FXCollections.observableArrayList();
    //ObservableList<DocRiskFactorTableModel> docRiskFactorTableModelObservableList = FXCollections.observableArrayList();
    ObservableList<DocAdverseReactionTableModel>docAdverseReactionTableModelObservableList = FXCollections.observableArrayList();
    ObservableList<ReportsTableModel> reportsTableModelObservableList = FXCollections.observableArrayList();
    ObservableList<Integer> choiceBoxModelObservableList = FXCollections.observableArrayList();
    private UserModel userInfo;

    public void setUserInfo(UserModel user){
        this.userInfo = user;
    }
    public UserModel getUserInfo(){
        return this.userInfo;
    }


    public  void initialize (URL url, ResourceBundle resourse) {

        reportView.setVisible(false);
        patientsView.setVisible(false);
        homePage.setVisible(true);
        slider.setTranslateX(0); //se inizializzata a -176 il menu non compare

        Platform.runLater(() -> {
        initializeTables();

        //ArrayList<TableColumn<ReportsTableModel,String>> columns = getReportsTableToArray();
        //System.out.println(reportIdColumn.setCellValueFactory(new PropertyValueFactory<>("reportId")));
        Query.executeReportsUpdate(reportsTableModelObservableList, reportsTable, reportsTextField);

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
            slidePatientTable.setNode(reportsTable);
            slideReportTable.setNode(patientIdTable);

            slideMenu.setToX(0);
            slidePatientTable.setToX(0);
            slideReportTable.setToX(0);

            slideMenu.play();
            slidePatientTable.play();
            slideReportTable.play();

            slider.setTranslateX(-176);

            slideMenu.setOnFinished((ActionEvent e) -> {

                reportsTable.prefWidthProperty().bind(patientsView.widthProperty());
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
            slidePatientTable.setNode(reportsTable);
            slideReportTable.setNode(patientIdTable);

            slideMenu.setToX(-176);
            slidePatientTable.setToX(-176);
            slideReportTable.setToX(-176);

            slideMenu.play();
            slidePatientTable.play();
            slideReportTable.play();
            slider.setTranslateX(0);

            reportsTable.prefWidthProperty().bind(stage.widthProperty());
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
        /*
        riskFactorTable.setOnMousePressed(event -> {
            String clicked = riskFactorTable.getSelectionModel().getSelectedItem().getRiskFactor();
            commitRiskFactor.setText(clicked);
            System.out.println(clicked);
        });

         */
        adverseReactionTable.setOnMousePressed(event -> {
            String clicked = adverseReactionTable.getSelectionModel().getSelectedItem().getAdverseReaction();
            commitAdverseReaction.setText(clicked);
            System.out.println(clicked);
        });

        reportsTable.setOnMousePressed(event -> {
            //INSERT REAZIONE AVVERSA


            //Recupero le informazioni dalla vista
            String clickedReportId = reportsTable.getSelectionModel().getSelectedItem().getReportId();
            System.out.println(clickedReportId);
            String clickedPatientId = reportsTable.getSelectionModel().getSelectedItem().getPatientId();
            System.out.println(clickedPatientId);
            String clickedAdReactionDate = reportsTable.getSelectionModel().getSelectedItem().getAdReactionDate();
            System.out.println(clickedAdReactionDate);
            String clickedDoctorId = reportsTable.getSelectionModel().getSelectedItem().getDoctorId();
            System.out.println(clickedDoctorId);
            String clickedAdverseReaction = reportsTable.getSelectionModel().getSelectedItem().getAdverseReaction();
            System.out.println(clickedAdverseReaction);
            String clickedReportDate = reportsTable.getSelectionModel().getSelectedItem().getReportDate();
            System.out.println(clickedReportDate);
            String description = descriptionArea.getText();
            System.out.println(description);
            Integer gravityLevel = choiceBox.getValue();
            System.out.println(gravityLevel);
            //Inserisco le informazioni negli oggetti rispettivi
            ReportsTableModel reportModel = retriveReportInfo(clickedReportId,clickedPatientId,clickedDoctorId,clickedAdReactionDate,clickedAdverseReaction,clickedReportDate);
            DocAdverseReactionTableModel adverseReactionModel = retriveAdReactionInfo(clickedAdverseReaction, gravityLevel, description);

            //Apertura view vaccini
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(DocController.class.getResource("VaccineView.fxml")); // [b]<== path to .fxml is correct[/b]
                try {
                    Parent root = (Parent) loader.load();  // [b]<=== ERROR here! The path in getResource() is correct[/b]
                    ReportDetailsController reportDetailsController = loader.getController();
                    reportDetailsController.setReportDetailsInfo(reportModel,adverseReactionModel);
                    Stage docStage = new Stage();
                    Scene scene = new Scene(root);
                    docStage.setScene(scene);
                    docStage.show();

                } catch (IOException e) {
                    Logger.getLogger(DocController.class.getName()).log(Level.SEVERE, null, e);
                    System.out.println("Loader error : " + e);
                }
            } catch (IllegalStateException exception) {
                System.out.println(exception.getMessage());
                System.out.println(exception.getCause());
            } catch (Exception e) {
                e.printStackTrace();
                e.getCause();
            }
        });

        //Inserisce una nuova segnalazione
        sendReportButton.setOnMousePressed( event -> {

            if(commitPatientID.getText().isBlank() || commitAdverseReaction.getText().isBlank() || choiceBox.getSelectionModel().getSelectedItem() == null){
                checkCommitLabel.setText("Please fill in all fields");
            }
            else {
                    String query = Query.insertQuery(commitPatientID.getText(),commitAdverseReaction.getText(),commitAdReactionDate.getText(),userInfo.getUser());
                    boolean resultQuery = Query.executeNewInsert(query);
                    System.out.println(resultQuery);
                    if(resultQuery == true) {
                        checkCommitLabel.setText("Please enter a correct date (YYYY-MM-DD)");
                    }
                    else{
                        Query.executeReportsUpdate(reportsTableModelObservableList, reportsTable, reportsTextField);

                        checkCommitLabel.setText("");
                        commitPatientID.setText("");
                        commitAdverseReaction.setText("");
                        commitAdReactionDate.setText("");

                        //Controllo del superamento del limite delle 50 segnalazioni
                        checkReportLimit();
                    }
            }
        });

        });
    }

    public void initializeTables () {

            DataBaseConnection connetNow = new DataBaseConnection();
            Connection connectDB = connetNow.getConnection();

            //Query SQL eseguita su postgreSQL
            String patientIdQuery = "SELECT * FROM \"Doctor\".\"patientList\" ORDER BY id ASC";
            String adverseReactionQuery = "SELECT * FROM \"Doctor\".\"reazioniAvverse\" ORDER BY nome ASC";

            try {
                Statement patientStatement = connectDB.createStatement();
                //   Statement riskFactorStatement = connectDB.createStatement();
                Statement adverseReactionStatement = connectDB.createStatement();

                ResultSet patientQueryOutput = patientStatement.executeQuery(patientIdQuery);

                ResultSet adverseReactionQueryOutput = adverseReactionStatement.executeQuery(adverseReactionQuery);

                while (patientQueryOutput.next()) {
                    //il parametro di getString è una stringa che riporta il nome esatto della colonna all'interno del Database su pSQL
                    String queryPatientId = patientQueryOutput.getString("id");
                    //Popolazione ObservableList
                    docPatientIDTableModelObservableList.add(new DocPatientIDTableModel(queryPatientId));
                }
                while (adverseReactionQueryOutput.next()) {
                    //il parametro di getString è una stringa che riporta il nome esatto della colonna all'interno del Database su pSQL
                    String queryAdverseReaction = adverseReactionQueryOutput.getString("nome");
                    Integer queryGravityLevel = adverseReactionQueryOutput.getInt("gravità");
                    String queryDescription = adverseReactionQueryOutput.getString("descrizione");
                    //Popolazione ObservableList
                    docAdverseReactionTableModelObservableList.add(new DocAdverseReactionTableModel(queryAdverseReaction,queryGravityLevel ,queryDescription ));
                }

                //Implementazione dell'observableArrayList per la gravità
                gravityLevelImplementation();

                //PropertyValueFactory corrisponde agli stessi campi della classe ...Model
                patientIdColumn.setCellValueFactory(new PropertyValueFactory<>("patientID"));
                //System.out.println(patientIdColumn.setCellValueFactory(new PropertyValueFactory<>("patientID")));
                //riskFactorColumn.setCellValueFactory(new PropertyValueFactory<>("riskFactor"));
                adverseReactionColumn.setCellValueFactory(new PropertyValueFactory<>("adverseReaction"));


                patientIdTable.setItems(docPatientIDTableModelObservableList);
                // riskFactorTable.setItems(docRiskFactorTableModelObservableList);
                adverseReactionTable.setItems(docAdverseReactionTableModelObservableList);
                //inizializzazione dei valori della check box per la gravità
                choiceBox.setItems(choiceBoxModelObservableList);

                //Filtraggio dati mentre si cerca nella tabella
                FilteredList<DocPatientIDTableModel> filteredPatientIdTableData = new FilteredList<>(docPatientIDTableModelObservableList, b -> true);
                // FilteredList<DocRiskFactorTableModel> filteredRiskFactorTableData = new FilteredList<>(docRiskFactorTableModelObservableList, b->true);
                FilteredList<DocAdverseReactionTableModel> filteredAdverseReactionTableData = new FilteredList<>(docAdverseReactionTableModelObservableList, b -> true);

                patientIdTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                    filteredPatientIdTableData.setPredicate(DocPatientIDTableModel -> {
                        //SE non si riempie il campo mostra tutti i pazienti
                        if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                            return true;
                        }
                        String searchKeyword = newValue.toLowerCase();
                        if (DocPatientIDTableModel.getPatientID().toLowerCase().indexOf(searchKeyword) > -1)
                            return true; //C'è un match
                        else
                            return false; //no match
                    });
                });

                adReactionTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                    filteredAdverseReactionTableData.setPredicate(DocAdverseReactionTableModel -> {
                        //SE non si riempie il campo mostra tutti i pazienti
                        if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                            return true;
                        }
                        String searchKeyword = newValue.toLowerCase();
                        if (DocAdverseReactionTableModel.getAdverseReaction().toLowerCase().indexOf(searchKeyword) > -1)
                            return true; //C'è un match
                        else
                            return false; //no match
                    });
                });

                SortedList<DocPatientIDTableModel> sortedPatientIdTableData = new SortedList<>(filteredPatientIdTableData);

                SortedList<DocAdverseReactionTableModel> sortedAdverseReactionTableData = new SortedList<>(filteredAdverseReactionTableData);

                //bind dei risultati ordinati con la Table View
                sortedPatientIdTableData.comparatorProperty().bind(patientIdTable.comparatorProperty());

                sortedAdverseReactionTableData.comparatorProperty().bind(adverseReactionTable.comparatorProperty());

                //Applico i dati filtrati ed ordinati alla Table View
                patientIdTable.setItems(sortedPatientIdTableData);

                adverseReactionTable.setItems(sortedAdverseReactionTableData);

            } catch (PSQLException e) {
                System.out.println(e.getMessage());
            } catch (SQLException e) {
                Logger.getLogger(DocController.class.getName()).log(Level.SEVERE, null, e);
                e.printStackTrace();
            }
        }

        public void viewPatientsButtonOnAction (ActionEvent actionEvent){
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

        public void checkReportLimit(){

            DataBaseConnection connetNow = new DataBaseConnection();
            Connection connectDB = connetNow.getConnection();
            String checkReportQuery = "SELECT COUNT(*) FROM \"Doctor\".segnalazione";

            try{
                Statement checkReportStatement = connectDB.createStatement();
                ResultSet checkReportQueryOutput = checkReportStatement.executeQuery(checkReportQuery);
                while (checkReportQueryOutput.next()){
                           if(checkReportQueryOutput.getInt(1) % 50 == 0){
                                WarningTask.sendWarning();
                           }
                }
            }
            catch (PSQLException psqlException){
                System.out.println(psqlException.getMessage());
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }

        public void gravityLevelImplementation(){
        for(int i = 1; i <= MAX_GRAVITY_LEVEL ; i++) {
            choiceBoxModelObservableList.add(i);
            }
        }

        public ReportsTableModel retriveReportInfo(String report, String patient, String doctor, String adReactionDate, String adverseReaction, String reportDate){
        ArrayList<String> arrayList = new ArrayList<>();
        ReportsTableModel reportModel = new ReportsTableModel(arrayList);
        reportModel.setReportId(report);
        reportModel.setPatientId(patient);
        reportModel.setDoctorId(doctor);
        reportModel.setAdReactionDate(adReactionDate);
        reportModel.setAdverseReaction(adverseReaction);
        reportModel.setReportDate(reportDate);
        return reportModel;
        }

        public DocAdverseReactionTableModel retriveAdReactionInfo(String name, Integer gravityLevel, String description ){
        DocAdverseReactionTableModel adReactionModel = new DocAdverseReactionTableModel(name,gravityLevel,description);
        return adReactionModel;
        }



}