package com.vaxreact;

import com.jfoenix.controls.JFXButton;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
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

import static com.vaxreact.Query.getLastInsertQuery;


public class DocController implements Initializable, Operation{

    public static final  Integer MAX_GRAVITY_LEVEL = 5;

    @FXML
    private Pane newReportView;

    @FXML
    private Pane homePage;

    @FXML
    private Pane reportsView;

    @FXML
    private Pane patientsInfoView;

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
    private TableView<TableModel> adverseReactionTable;

    @FXML
    private TableColumn<TableModel, String> name;

    @FXML
    private TableColumn<TableModel, String> gravity;

    @FXML
    private TableColumn<TableModel, String> description;

    @FXML
    private TextField adReactionTextField;



    @FXML
    private TableView<TableModel> reportsTable;

    @FXML
    public TableColumn<TableModel, String> reportId;

    @FXML
    private TableColumn<TableModel, String> patientId;

    @FXML
    private TableColumn<TableModel, String> adverseReaction;

    @FXML
    private TableColumn<TableModel, String> adReactionDate;

    @FXML
    private TableColumn<TableModel, String> reportDate;

    @FXML
    private TableColumn<TableModel, String> doctorId;

    @FXML
    private TextField reportsTextField;

    @FXML
    private TableView<TableModel> patientIdTable;

    @FXML
    private TableColumn<TableModel, String> id;

    @FXML
    private TextField patientIdTextField;

    @FXML
    private TableView<TableModel> patientsInfoTable;

    @FXML
    private TableColumn<TableModel, String> patientIdPatientsInfoColumn;

    @FXML
    private  TableColumn<TableModel, String> dateOfBirthColumn;

    @FXML
    private TableColumn<TableModel, String> residenceColumn;

    @FXML
    private TableColumn<TableModel, String> professionColumn;

    @FXML
    private TableColumn<TableModel, String> adverseReactionsColumn;

    @FXML
    private TextField patientsInfoTextField;






    @FXML
    private Text commitAdverseReaction;

    @FXML
    private Text commitPatientID;

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





    @FXML
    private JFXButton reportHighButton;
    @FXML
    private JFXButton viewReportsHighButton;
    @FXML
    private JFXButton patientsInfoHighButton;


    ObservableList<TableModel> docPatientIDTableModelObservableList = FXCollections.observableArrayList();
    ObservableList<TableModel> docPatientInfoTableModelObservableList = FXCollections.observableArrayList();
    ObservableList<TableModel>docAdverseReactionTableModelObservableList = FXCollections.observableArrayList();
    ObservableList<TableModel> docReportsTableModelObservableList = FXCollections.observableArrayList();
    ObservableList<Integer> choiceBoxModelObservableList = FXCollections.observableArrayList();
    private UserModel userInfo;

    public void setUserInfo(UserModel user){
        this.userInfo = user;
    }

    public void reportButtonOnAction() {
        showNewReport();
    }
    public void reportHighButtonOnActon() {
        showNewReport();
    }
    @FXML
    public void viewReportsHighButtonOnActon() {
        showReports();
    }
    //sarebbe view reports
    public void viewPatientsButtonOnAction (){
        showReports();
    }
    @FXML
    void patientsInfoHighButtonOnAction() {
        showPatientsInfo();
    }
    @FXML
    void patientsInfoButtonOnAction() {
        showPatientsInfo();
    }

    void showPatientsInfo(){
        homePage.setVisible(false);
        reportsView.setVisible(false);
        newReportView.setVisible(false);
        patientsInfoView.setVisible(true);
        patientsInfoHighButton.setStyle("-fx-border-color: blue; -fx-border-style: solid solid solid solid");
        viewReportsHighButton.setStyle("-fx-border-color: #d5d5d5");
        reportHighButton.setStyle("-fx-border-color: #d5d5d5");
    }
    void showReports(){
        patientsInfoView.setVisible(false);
        homePage.setVisible(false);
        newReportView.setVisible(false);
        reportsView.setVisible(true);
        viewReportsHighButton.setStyle("-fx-border-color: blue; -fx-border-style: solid solid solid solid");
        reportHighButton.setStyle("-fx-border-color: #d5d5d5");
        patientsInfoHighButton.setStyle("-fx-border-color: #d5d5d5; -fx-border-style: solid solid solid hidden");
    }
    void showNewReport(){
        patientsInfoView.setVisible(false);
        homePage.setVisible(false);
        reportsView.setVisible(false);
        newReportView.setVisible(true);
        reportHighButton.setStyle("-fx-border-color: blue; -fx-border-style: solid solid solid solid");
        patientsInfoHighButton.setStyle("-fx-border-color: #d5d5d5; -fx-border-style: solid hidden solid hidden" );
        viewReportsHighButton.setStyle("-fx-border-color: #d5d5d5; -fx-border-style: solid solid solid solid");
    }

    public  void initialize (URL url, ResourceBundle resource) {

        patientsInfoView.setVisible(false);
        newReportView.setVisible(false);
        reportsView.setVisible(false);
        homePage.setVisible(true);
        slider.setTranslateX(0); //se inizializzata a -176 il menu non compare

        reportsTable.prefWidthProperty().bind(reportsView.widthProperty());
        reportsTable.prefHeightProperty().bind(reportsView.heightProperty());
        patientsInfoTable.prefWidthProperty().bind(patientsInfoView.widthProperty());
        patientsInfoTable.prefHeightProperty().bind(patientsInfoView.heightProperty());


        Platform.runLater(() -> {

            try {
                updateTables();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            gravityLevelImplementation();

            exit.setOnMouseClicked(event -> System.exit(0));

        menu.setOnMouseClicked(event -> {
            TranslateTransition slideMenu = new TranslateTransition();
            TranslateTransition slideReportTable = new TranslateTransition();
            TranslateTransition slidePatientsInfoTable = new TranslateTransition();
            TranslateTransition slideNewReportPane = new TranslateTransition();

            slideMenu.setDuration(Duration.seconds(0.5));
            slideReportTable.setDuration(Duration.seconds(0.5));
            slidePatientsInfoTable.setDuration(Duration.seconds(0.5));
            slideNewReportPane.setDuration(Duration.seconds(0.5));

            slideMenu.setNode(slider);
            slideReportTable.setNode(reportsTable);
            slidePatientsInfoTable.setNode(patientsInfoTable);
            slideNewReportPane.setNode(newReportView);


            slideMenu.setToX(0);
            slideReportTable.setToX(0);
            slidePatientsInfoTable.setToX(0);
            slideNewReportPane.setToX(0);

            slideMenu.play();
            slideReportTable.play();
            slidePatientsInfoTable.play();
            slideNewReportPane.play();


            slider.setTranslateX(-176);

            slideMenu.setOnFinished((ActionEvent e) -> {

                reportsTable.prefWidthProperty().bind(reportsView.widthProperty());
                patientsInfoTable.prefWidthProperty().bind(patientsInfoView.widthProperty());
                reportsTable.prefHeightProperty().bind(reportsView.heightProperty());
                patientsInfoTable.prefHeightProperty().bind(patientsInfoView.heightProperty());

                menu.setVisible(false);
                menuBack.setVisible(true);
            });
        });

            menuBack.setOnMouseClicked(event -> {

                TranslateTransition slideMenu = new TranslateTransition();
                TranslateTransition slideNewReportPane = new TranslateTransition();
                TranslateTransition slideReportTable = new TranslateTransition();
                TranslateTransition slidePatientsInfoTable = new TranslateTransition();

                slideMenu.setDuration(Duration.seconds(0.5));
                slideNewReportPane.setDuration(Duration.seconds(0.5));
                slideReportTable.setDuration(Duration.seconds(0.5));
                slidePatientsInfoTable.setDuration(Duration.seconds(0.5));

                slideMenu.setNode(slider);
                slideReportTable.setNode(reportsTable);
                slidePatientsInfoTable.setNode(patientsInfoTable);
                slideNewReportPane.setNode(newReportView);


                slideMenu.setToX(-176);
                slideNewReportPane.setToX(-176);
                slideReportTable.setToX(-176);
                slidePatientsInfoTable.setToX(-176);

                slideMenu.play();
                slideNewReportPane.play();
                slideReportTable.play();
                slidePatientsInfoTable.play();
                slider.setTranslateX(0);


            reportsTable.prefWidthProperty().bind(stage.widthProperty());
            patientsInfoTable.prefWidthProperty().bind(stage.widthProperty());

            slideMenu.setOnFinished((ActionEvent e) -> {
                menu.setVisible(true);
                menuBack.setVisible(false);
            });
        });

            patientIdTable.setOnMousePressed(event -> {
                String clicked = patientIdTable.getSelectionModel().getSelectedItem().arrayList.get(0);
                commitPatientID.setText(clicked);
            });

            adverseReactionTable.setOnMousePressed(event -> {
            String clicked = adverseReactionTable.getSelectionModel().getSelectedItem().arrayList.get(0);
            commitAdverseReaction.setText(clicked);
        });


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

            // CLICCO SULLE VOCI DELLA TABELLA PATIENT'S INFO PER VEDERNE I DETTAGLI
            patientsInfoTable.setOnMousePressed(event -> {
                String clickedPatientId = patientsInfoTable.getSelectionModel().getSelectedItem().getCodice();
                //Apertura view vaccini
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(DocController.class.getResource("PatientInfoView.fxml")); // [b]<== path to .fxml is correct[/b]
                    try {
                        Parent root = loader.load();  // [b]<=== ERROR here! The path in getResource() is correct[/b]
                        PatientInfoDetailsController patientInfoDetails = loader.getController();
                        patientInfoDetails.setPatientsInfoDetails(clickedPatientId);
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


            //Inserisce una nuova segnalazione
            sendReportButton.setOnMousePressed( event -> {

            if(commitPatientID.getText().isBlank() || commitAdverseReaction.getText().isBlank() || choiceBox.getSelectionModel().getSelectedItem() == null){
                checkCommitLabel.setText("Please fill in all fields");
            }
            else {
                    String query = Query.insertQuery(commitPatientID.getText(), commitAdverseReaction.getText(), commitAdReactionDate.getText(), userInfo.getUser(), choiceBox.getValue(), descriptionArea.getText());
                boolean resultQuery = false;
                try {
                    resultQuery = Query.executeNewInsert(query);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                if(resultQuery) {
                        checkCommitLabel.setText("Please enter a correct date (YYYY-MM-DD)");
                    }
                    else{
                        try {
                            updateTables();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        checkCommitLabel.setTextFill(Color.DARKGREEN);
                        checkCommitLabel.setText("Report correctly sent");
                        commitPatientID.setText("");
                        commitAdverseReaction.setText("");
                        commitAdReactionDate.setText("");

                        //se la gravità è alta, si controlla le segnalazioni recenti
                        if(choiceBox.getValue()>=3) {
                            try {
                                tryToInsertSeriousReport();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
            }
        });

        });
    }



    /*
        Questo metodo consente di collegare i report e vaccini che sono vicini temporalente
     */
    public void tryToInsertSeriousReport() throws SQLException {

        Connection connectDB = CreateConnection.getInstance().getConnection();
        Statement getLastInsertStatement = connectDB.createStatement();
        ResultSet getLastInsertQueryOutput = getLastInsertStatement.executeQuery(getLastInsertQuery());

        try {


            assert getLastInsertQueryOutput != null;
            if (!getLastInsertQueryOutput.next()) {
                System.exit(0);
            }
            Integer reportIdentifier = getLastInsertQueryOutput.getInt(1);

            Connection connectDB1 = CreateConnection.getInstance().getConnection();
            Statement checkLastInsertStatement = connectDB1.createStatement();
            ResultSet checkLastInsertQueryOutput = checkLastInsertStatement.executeQuery(Query.getVerificationInTheLastMonthQuery(reportIdentifier));

            assert checkLastInsertQueryOutput != null;
            if (!checkLastInsertQueryOutput.next()) {
                System.exit(0);
            } else {
                ArrayList<String> arrayList = Query.getRowValue(checkLastInsertQueryOutput);
                String query = Query.insertVaccination_ReportQuery(arrayList.get(0), arrayList.get(3), arrayList.get(1), arrayList.get(2));
                boolean resultQuery = Query.executeNewInsert(query);
                if (resultQuery) {
                    checkCommitLabel.setText("L'insert in Vaccinazione_Segnalazione NON è avvenuto correttamente");
                } else {
                    System.out.println("L'insert in Vaccinazione_Segnalazione è avvenuto correttamente.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connectDB.close();
            } catch (Exception e) { /* Ignored */ }
            try {
                getLastInsertStatement.close();
            } catch (Exception e) { /* Ignored */ }

        }
    }

        public void gravityLevelImplementation(){
        for(int i = 1; i <= MAX_GRAVITY_LEVEL ; i++) {
            choiceBoxModelObservableList.add(i);
            }
        choiceBox.setItems(choiceBoxModelObservableList);
        }

        public ArrayList<String> retriveReportInfo(String report, String patient, String doctor, String adReactionDate, String adverseReaction, String reportDate){

        ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add(report);
            arrayList.add(patient);
            arrayList.add(doctor);
            arrayList.add(adReactionDate);
            arrayList.add(adverseReaction);
            arrayList.add(reportDate);

            return arrayList;
        }

        /*
        Aggiorna le tabelle del programma (tabella dei report, dei pazienti, delle reazioni avverse e degli ID dei pazienti).
         */
        public void updateTables() throws SQLException {

            Query query = new Query();
            ArrayList<TableColumn<TableModel,String>> arrayList = createReportsTableObject();
            query.executeUpdate(docReportsTableModelObservableList, reportsTable, reportsTextField, arrayList,1);

            arrayList= createPatientIDTableObject();
            query.executeUpdate(docPatientIDTableModelObservableList, patientIdTable, patientIdTextField, arrayList,2);

            arrayList= createAdverseReactionTableObject();
            query.executeUpdate(docAdverseReactionTableModelObservableList, adverseReactionTable, adReactionTextField, arrayList,3);

            arrayList = createPatientInfoTableObject();
            query.executeUpdate(docPatientInfoTableModelObservableList, patientsInfoTable, patientsInfoTextField, arrayList, 4);
        }

    public ArrayList<TableColumn<TableModel,String>> createPatientInfoTableObject(){
        ArrayList<TableColumn<TableModel,String>> arrayList = new ArrayList<>();
        arrayList.add(patientIdPatientsInfoColumn);
        arrayList.add(dateOfBirthColumn);
        arrayList.add(residenceColumn);
        arrayList.add(professionColumn);
        arrayList.add(adverseReactionsColumn);

        return arrayList;
    }

        public ArrayList<TableColumn<TableModel,String>> createReportsTableObject(){
            ArrayList<TableColumn<TableModel,String>> arrayList = new ArrayList<>();

            arrayList.add(reportId);
            arrayList.add(adReactionDate);
            arrayList.add(adverseReaction);
            arrayList.add(patientId);
            arrayList.add(doctorId);
            arrayList.add(reportDate);
            arrayList.add(gravity);
            arrayList.add(description);

            return arrayList;
        }

        public ArrayList<TableColumn<TableModel,String>> createPatientIDTableObject(){
            ArrayList<TableColumn<TableModel,String>> arrayList = new ArrayList<>();
            arrayList.add(id);

            return arrayList;
        }
        public ArrayList<TableColumn<TableModel,String>> createAdverseReactionTableObject(){
            ArrayList<TableColumn<TableModel,String>> arrayList = new ArrayList<>();
            arrayList.add(name);

            return arrayList;
        }
}