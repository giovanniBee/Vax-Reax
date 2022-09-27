package com.vaxreact;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.vaxreact.Query.getNReports;


public class FarmaController implements Initializable, Operation {

    @FXML
    private Pane homePage;
    @FXML
    private Pane reportsView;
    @FXML
    private Pane controlPhaseView;
    @FXML
    private Pane basicAnalysisView;

    @FXML
    private TableView<TableModel> reportsTable;

    @FXML
    private TableColumn<TableModel,String> adverseReactionColumn;

    @FXML
    private TableColumn<TableModel,String> adverseReactionDateColumn;

    @FXML
    private TableColumn<TableModel,String> doctorIdColumn;

    @FXML
    private TableColumn<TableModel,String> patientIdColumn;

    @FXML
    private TableColumn<TableModel,String> reportDateColumn;

    @FXML
    private TableColumn<TableModel,String> reportIdColumn;

    @FXML
    private TableColumn<TableModel, String> gravityColumn;

    @FXML
    private TableColumn<TableModel, String> descriptionColumn;

    @FXML
    private JFXButton reportsForVaxButton;

    @FXML
    private JFXButton reportsForGravityButton;


    @FXML
    private JFXButton reportsForProvinceButton;

    @FXML
    private JFXButton reportsForSiteButton;

    @FXML
    private TableView<TableModel> reportBindToVaxTable;

    @FXML
    private TableColumn<TableModel, String > numOfReport;

    @FXML
    private TableColumn<TableModel, String > vaccine;

    @FXML
    private TableView<TableModel> reportBindToCityTable;

    @FXML
    private TableColumn<TableModel, String > city;

    @FXML
    private TableColumn<TableModel, String> numOfReportForCity;

    @FXML
    private TextField reportBindToVaxTextField;

    @FXML
    private TextField reportsTextField;

    @FXML
    private Label checkCommitLabel;



    @FXML
    private Label notificationArea;

    @FXML
    private Button sendControlPhaseButton;

    @FXML
    private TextArea controlPhaseDescription;

    @FXML
    private TextField controlPhaseNameTextField;

    ObservableList<TableModel> farmaReportsTableModelObservableList = FXCollections.observableArrayList();


    private UserModel userInfo;

    public void setUserInfo(UserModel user){
        this.userInfo = user;
    }

    public void initialize(URL url, ResourceBundle rs){

        try {
            setNotificationMessage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //Binding della tabella con la View per effettuare il resizing
        reportsTable.prefWidthProperty().bind(reportsView.widthProperty());
        reportsTable.prefHeightProperty().bind(reportsView.heightProperty());


        setHomePageView();
        Platform.runLater(() ->{

            try {
                updateTables();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            //setNotificationMessage();

            //CLICCO SULLE VOCI DELLA TABELLA REPORT PER VEDERNE I DETTAGLI
            reportsTable.setOnMousePressed(event -> {

                //Recupero le informazioni dalla vista
                String clickedReportId = reportsTable.getSelectionModel().getSelectedItem().getCodice();
                String clickedPatientId = reportsTable.getSelectionModel().getSelectedItem().getCodicePaziente();
                String clickedAdReactionDate = reportsTable.getSelectionModel().getSelectedItem().getDataReazione();
                String clickedDoctorId = reportsTable.getSelectionModel().getSelectedItem().getUserMedico();
                String clickedAdverseReaction = reportsTable.getSelectionModel().getSelectedItem().getReazioneAvversa();
                String clickedReportDate = reportsTable.getSelectionModel().getSelectedItem().getDataReport();
                //Inserisco le informazioni negli oggetti rispettivi
                ArrayList<String> reportModel = retriveReportInfo(clickedReportId,clickedPatientId,clickedDoctorId,clickedAdReactionDate,clickedAdverseReaction,clickedReportDate);


                //Apertura view vaccini
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(DocController.class.getResource("VaccineView.fxml")); // [b]<== path to .fxml is correct[/b]
                    try {
                        Parent root = loader.load();  // [b]<=== ERROR here! The path in getResource() is correct[/b]
                        ReportDetailsController reportDetailsController = loader.getController();
                        reportDetailsController.setReportDetailsInfo(reportModel);
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
                } catch (Exception e) {
                    e.printStackTrace();
                    e.getCause();
                }
            });
        });


        sendControlPhaseButton.setOnMousePressed(event -> {

            if(controlPhaseDescription.getText().isBlank() || controlPhaseNameTextField.getText().isBlank()){
                checkCommitLabel.setText("Please fill in all fields!");
            }else {
                    String query = Query.insertControlPhase(controlPhaseNameTextField.getText(), userInfo.getUser(), controlPhaseDescription.getText());
                boolean resultQuery = false;
                try {
                    resultQuery = Query.executeNewInsert(query);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if (resultQuery) {
                        checkCommitLabel.setTextFill(Color.DARKRED);
                        checkCommitLabel.setText("Not sent");
                    } else {

                        checkCommitLabel.setTextFill(Color.DARKGREEN);
                        checkCommitLabel.setText("Sent correctly");

                    }
            }
        });




        reportsForSiteButton.setOnMousePressed(event -> {

            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(DocController.class.getResource("ReportForSiteView.fxml")); // [b]<== path to .fxml is correct[/b]
                try {
                    Parent root = loader.load();  // [b]<=== ERROR here! The path in getResource() is correct[/b]
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

            } catch (Exception e) {
                e.printStackTrace();
                e.getCause();
            }

        });

        reportsForProvinceButton.setOnMousePressed(event -> {

            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(DocController.class.getResource("ReportForProvinceView.fxml")); // [b]<== path to .fxml is correct[/b]
                try {
                    Parent root = loader.load();  // [b]<=== ERROR here! The path in getResource() is correct[/b]
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

            } catch (Exception e) {
                e.printStackTrace();
                e.getCause();
            }

        });

        reportsForGravityButton.setOnMousePressed(event -> {

            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(DocController.class.getResource("ReportForGravityView.fxml")); // [b]<== path to .fxml is correct[/b]
                try {
                    Parent root = loader.load();  // [b]<=== ERROR here! The path in getResource() is correct[/b]
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

            } catch (Exception e) {
                e.printStackTrace();
                e.getCause();
            }

        });

        reportsForVaxButton.setOnMousePressed(event -> {

            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(DocController.class.getResource("ReportForVaxView.fxml")); // [b]<== path to .fxml is correct[/b]
                try {
                    Parent root = loader.load();  // [b]<=== ERROR here! The path in getResource() is correct[/b]
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

            } catch (Exception e) {
                e.printStackTrace();
                e.getCause();
            }

        });

    }
    public static boolean isWeekend(final Date d)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);

        int day = cal.get(Calendar.DAY_OF_WEEK);
        return day == Calendar.SATURDAY || day == Calendar.SUNDAY;
    }

    /*
        Metodo che imposta le notifiche richieste sull'area di notifica

     */
    public void setNotificationMessage() throws SQLException {
        //50 segnalazioni dall'ultima notifica
        Connection connectDB = CreateConnection.getInstance().getConnection();
        Statement statement = connectDB.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet queryOutput = statement.executeQuery(getNReports());

        if (queryOutput.next()) {
            if (queryOutput.getInt(1) % 50 == 0)
                notificationArea.setText("\n50 segnalazioni raggiunte dall'ultima notifica di numero");
        }

        //notifica di weekend
        Date today = new Date();
        if (isWeekend(today))
            notificationArea.setText("\nE' il weekend, ricorda di controllare le segnalazioni!");


        //notifiche vaccini con piu reazioni avverse
        VaccineModel vaccineModel = new VaccineModel();
        String[] vaccini = vaccineModel.toStringVector();

        //ciclo while per le notifiche con l'enhanced for
        for (String s : vaccini) {
            queryOutput = statement.executeQuery(Query.getControlQuery(s));
            queryOutput.next();
            int NOTIFICATION_THRESHOLD = 5;
            if (queryOutput.getInt(1) >= NOTIFICATION_THRESHOLD) {
                String notificationMessage = "Il vaccino " + s + " ha accumulato più di 5 segnalazioni di gravità superiore a" +
                        " 3 questo mese";
                notificationArea.setText(notificationArea.getText() + "\n" + notificationMessage);
            }
        }

    }
    @FXML
    private void basicAnalysisHighButtonOnActon() {
        setBasicAnalysisView();
    }
    @FXML
    private void reportsHighButtonOnActon() {
        setReportsView();
    }
    @FXML
    private void basicAnalysisButtonOnAction() {
        setBasicAnalysisView();
    }
    @FXML
    private void reportsButtonOnAction() throws SQLException {
        setReportsView();
        updateTables();
    }
    @FXML
    private void controlPhaseOnAction() {
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
        basicAnalysisView.setVisible(false);
        controlPhaseView.setVisible(true);
    }

    @Override
    public void updateTables() throws SQLException {
        ArrayList<TableColumn<TableModel,String>> arrayListReports = createReportsTableObject();
        Query query = new Query();
        query.executeUpdate(farmaReportsTableModelObservableList, reportsTable, reportsTextField, arrayListReports,5);
    }

    /*
        Ritorna un Arraylist di stringhe, contenenti i dati sulle segnalazioni.
        Prende in input
    */
    @Override
    public ArrayList<String> retriveReportInfo(String report, String patient, String doctor, String adReactionDate, String adverseReaction, String reportDate) {

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(report);
        arrayList.add(patient);
        arrayList.add(doctor);
        arrayList.add(adReactionDate);
        arrayList.add(adverseReaction);
        arrayList.add(reportDate);

        return arrayList;
    }

    public ArrayList<TableColumn<TableModel,String>> createReportsTableObject(){
        ArrayList<TableColumn<TableModel,String>> arrayList = new ArrayList<>();
        arrayList.add(reportIdColumn);
        arrayList.add(adverseReactionDateColumn);
        arrayList.add(adverseReactionColumn);
        arrayList.add(patientIdColumn);
        arrayList.add(doctorIdColumn);
        arrayList.add(reportDateColumn);
        arrayList.add(gravityColumn);
        arrayList.add(descriptionColumn);


        return arrayList;
    }

}
