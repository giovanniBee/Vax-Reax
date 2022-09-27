package com.vaxreact;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ReportDetailsController extends DocController implements Initializable {

    @FXML
    private Text reportIdArea;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private Text doctorIdArea;

    @FXML
    private Text gravityArea;

    @FXML
    private Text adReactionDateArea;

    @FXML
    private TableView<VaccinationModel> vaccineTable;

    @FXML
    private TableColumn<VaccinationModel, String> dateColumn;

    @FXML
    private TableColumn<VaccinationModel, String> localityColumn;

    @FXML
    private  TableColumn<VaccinationModel, String> cityColumn;

    @FXML
    private TableColumn<VaccinationModel, String> typeColumn;

    @FXML
    private TableColumn<VaccinationModel, String> vaccineNameColumn;

    @FXML
    private TextField vaccineDataTextField;

    ObservableList<VaccinationModel> vaccinationModelObservableList = FXCollections.observableArrayList();

    private ArrayList<String> reportInfo;


    // private DocAdverseReactionTableModel adverseReactionInfo;

    public void setReportDetailsInfo(ArrayList<String> reportInfo){
        this.reportInfo = reportInfo;
    }

    public ArrayList<String> getReportInfo(){
        return this.reportInfo;
    }


    public void initialize(URL url, ResourceBundle rs){
        Platform.runLater(() -> {

            //System.out.println("QUI-"+ getAdverseReactionInfo().getDescriptionArea()+getAdverseReactionInfo().getGravityLevel()+"-");
            //updateReportDetails(getAdverseReactionInfo(),getReportInfo());

            Query query = new Query();

            String description = query.getCell(Query.getDescriptionQuery(getReportInfo().get(0)));
            String gravity = query.getCell(Query.getGravityQuery(getReportInfo().get(0)));
            System.out.println(description+" =descriptionArea "+gravity+" =gravityArea"+getReportInfo().get(1)+" =patientId");
            initializeView(getReportInfo(),description,gravity);
            updatePatientView(getReportInfo().get(0));
        });
    }


    public void updatePatientView(String reportId){

        vaccinationModelObservableList.clear();

        DataBaseConnection connetNow = new DataBaseConnection();
        Connection connectDB = connetNow.getConnection();
        String vaccineQuery = "SELECT DISTINCT V.\"Nome\", V.\"Tipo\", V.\"Sede\", SE.\"Provincia\", V.\"Data\" AS \"DataVaccino\" \n" +
                " FROM \"Segnalazione\" S, \"Vaccinazione\" V, \"Paziente\" P, \"Sede\" SE \n" +
                " WHERE  S.\"CodicePaziente\" = P.\"Codice\" AND P.\"Codice\" = V.\"CodicePaziente\" \n" +
                " AND\tS.\"Codice\" = ? AND SE.\"Nome\" = V.\"Sede\" \n" +
                " AND\tV.\"Data\" BETWEEN (S.\"DataReazione\" - INTERVAL '2 month') AND S.\"DataReazione\"";


        try {
            PreparedStatement vaccineStatement = connectDB.prepareStatement(vaccineQuery);
            Integer repId = Integer.valueOf(reportId);
            vaccineStatement.setInt(1,repId);
            //vaccineStatement.setString(2,date);
            ResultSet vaccineQueryOutput = vaccineStatement.executeQuery();
            while (vaccineQueryOutput.next()) {

                //il parametro di getString è una stringa che riporta il nome esatto della colonna all'interno del Database su pSQL
                String queryVaccineName = vaccineQueryOutput.getString("Nome");
                String queryType = vaccineQueryOutput.getString("Tipo");
                String querySite = vaccineQueryOutput.getString("Sede");
                String queryCity = vaccineQueryOutput.getString("Provincia");
                String queryDate = vaccineQueryOutput.getString("DataVaccino");


                vaccinationModelObservableList.add(new VaccinationModel(queryVaccineName, queryType, querySite, queryCity,queryDate));
                //PropertyValueFactory corrisponde agli stessi campi della classe ...Model
                vaccineNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
                typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
                localityColumn.setCellValueFactory(new PropertyValueFactory<>("site"));
                cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
                dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

                vaccineTable.setItems(vaccinationModelObservableList);

                FilteredList<VaccinationModel> filteredVaccineTableData = new FilteredList<>(vaccinationModelObservableList, b -> true);

                vaccineDataTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                    filteredVaccineTableData.setPredicate(VaccinationModel -> {
                        if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                            return true; //C'è un match
                        }
                        String searchKeyword = newValue.toLowerCase();
                        if (VaccinationModel.getName().toLowerCase().indexOf(searchKeyword) > -1) {
                            return true;
                        } else if (VaccinationModel.getType().toLowerCase().indexOf(searchKeyword) > -1) {
                            return true;

                        } else if (VaccinationModel.getSite().toLowerCase().indexOf(searchKeyword) > -1) {
                            return true;

                        } else if (VaccinationModel.getCity().toLowerCase().indexOf(searchKeyword) > -1) {
                            return true;

                        }else if (VaccinationModel.getDate().toLowerCase().indexOf(searchKeyword) > -1) {
                            return true;

                        }else {
                            return false;//no match found
                        }
                    });
                });
                SortedList<VaccinationModel> sortedVaccineTableData = new SortedList<>(filteredVaccineTableData);
                sortedVaccineTableData.comparatorProperty().bind(vaccineTable.comparatorProperty());
                vaccineTable.setItems(sortedVaccineTableData);
            }
        }//catch (PSQLException e){
         //   System.out.println(e.getMessage());
         //   System.out.println("Qui");
       // }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void initializeView(ArrayList<String> arrayList, String description, String gravity){

        descriptionArea.setText(description);
        gravityArea.setText(gravity);
        reportIdArea.setText(arrayList.get(0));
        adReactionDateArea.setText(arrayList.get(3));
        doctorIdArea.setText(arrayList.get(2));
    }


    /*
    public void updateReportDetails(DocAdverseReactionTableModel adverseReactionInfo, TableModel reportInfo){
        setReportIdArea(reportInfo.arrayList.get(0));
        //System.out.println(reportInfo.getReportIdArea());
        setDescriptionArea(adverseReactionInfo.getDescriptionArea());
        //System.out.println(adverseReactionInfo.getDescriptionArea());
        setGravityArea(adverseReactionInfo.getGravityLevel());
        //System.out.println(adverseReactionInfo.getGravityLevel());
        setDoctorIdArea(reportInfo.arrayList.get(5));
        //System.out.println(reportInfo.getDoctorIdArea());
        setAdReactionDateArea(reportInfo.arrayList.get(3));
        //System.out.println(reportInfo.getAdReactionDateArea());
    }
*/



    public Text getReportIdArea() {
        return reportIdArea;
    }

    public TextArea getDescriptionArea() {
        return descriptionArea;
    }

    public Text getDoctorIdArea() {
        return doctorIdArea;
    }

    public Text getGravityArea() {
        return gravityArea;
    }

    public Text getAdReactionDateArea() {
        return adReactionDateArea;
    }

    public void setReportIdArea(String reportIdArea) {
        this.reportIdArea.setText(reportIdArea);
    }

    public void setDescriptionArea(String descriptionArea) {
        this.descriptionArea.setText(descriptionArea);
    }

    public void setDoctorIdArea(String doctorIdArea) {
        this.doctorIdArea.setText(doctorIdArea);
    }

    public void setGravityArea(Integer gravityArea) {
        this.gravityArea.setText(gravityArea.toString());
    }

    public void setAdReactionDateArea(String adReactionDateArea) {
        this.adReactionDateArea.setText(adReactionDateArea);
    }
}
