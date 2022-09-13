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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ReportDetailsController extends DocController implements Initializable {

    @FXML
    private Text reportId;

    @FXML
    private TextArea description;

    @FXML
    private Text doctorId;

    @FXML
    private Text gravity;

    @FXML
    private Text adReactionDate;

    @FXML
    private TableView<VaccineModel> vaccineTable;

    @FXML
    private TableColumn<VaccineModel, String> dateColumn;

    @FXML
    private TableColumn<VaccineModel, String> localityColumn;

    @FXML
    private TableColumn<VaccineModel, String> typeColumn;

    @FXML
    private TableColumn<VaccineModel, String> vaccineNameColumn;

    @FXML
    private TextField vaccineDataTextField;

    ObservableList<VaccineModel>vaccineModelObservableList = FXCollections.observableArrayList();

    private ReportsTableModel reportInfo;
    private DocAdverseReactionTableModel adverseReactionInfo;

    public void setReportDetailsInfo(ReportsTableModel reportInfo, DocAdverseReactionTableModel adReactionModel){
        this.reportInfo = reportInfo;
        this.adverseReactionInfo = adReactionModel;
    }

    public ReportsTableModel getReportInfo(){
        return this.reportInfo;
    }

    public DocAdverseReactionTableModel getAdverseReactionInfo(){
        return  this.adverseReactionInfo;
    }

    public void initialize(URL url, ResourceBundle rs){
        Platform.runLater(() -> {
            updatePatientView(getReportInfo().getPatientId(),getReportInfo().getAdReactionDate());
            System.out.println("QUI-"+ getAdverseReactionInfo().getDescription()+getAdverseReactionInfo().getGravityLevel()+"-");
            updateReportDetails(getAdverseReactionInfo(),getReportInfo());

        });
    }

    public void updatePatientView(String patientId, String date){

        vaccineModelObservableList.clear();

        DataBaseConnection connetNow = new DataBaseConnection();
        Connection connectDB = connetNow.getConnection();
        String vaccineQuery = "SELECT \"vaccino\", \"tipo\", \"sede\", \"data\" FROM \"Doctor\".vaccinazione, \"Doctor\".segnalazione WHERE \"Doctor\".vaccinazione.\"idPaziente\" = \"Doctor\".segnalazione.\"patientId\" AND \"Doctor\".vaccinazione.\"codiceSegnalazione\" = \"Doctor\".segnalazione.\"reportId\" AND \"Doctor\".segnalazione.\"patientId\" = '"+ patientId +"' AND \"Doctor\".segnalazione.\"adReactionDate\" ='"+ date +"' AND \"Doctor\".vaccinazione.data BETWEEN (\"Doctor\".segnalazione.\"adReactionDate\" - INTERVAL '2 month') AND \"Doctor\".segnalazione.\"adReactionDate\" ";
        try {
            Statement vaccineStatement = connectDB.createStatement();
            ResultSet vaccineQueryOutput = vaccineStatement.executeQuery(vaccineQuery);
            while (vaccineQueryOutput.next()) {

                //il parametro di getString è una stringa che riporta il nome esatto della colonna all'interno del Database su pSQL
                String queryVaccineName = vaccineQueryOutput.getString("vaccino");
                String queryType = vaccineQueryOutput.getString("tipo");
                String queryLocality = vaccineQueryOutput.getString("sede");
                String queryDate = vaccineQueryOutput.getString("data");


                vaccineModelObservableList.add(new VaccineModel(queryVaccineName, queryType, queryLocality,queryDate));
                //PropertyValueFactory corrisponde agli stessi campi della classe ...Model
                vaccineNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
                typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
                localityColumn.setCellValueFactory(new PropertyValueFactory<>("locality"));
                dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

                vaccineTable.setItems(vaccineModelObservableList);

                FilteredList<VaccineModel> filteredVaccineTableData = new FilteredList<>(vaccineModelObservableList, b -> true);

                vaccineDataTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                    filteredVaccineTableData.setPredicate(VaccineModel -> {
                        if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                            return true; //C'è un match
                        }
                        String searchKeyword = newValue.toLowerCase();
                        if (VaccineModel.getName().toLowerCase().indexOf(searchKeyword) > -1) {
                            return true;
                        } else if (VaccineModel.getType().toLowerCase().indexOf(searchKeyword) > -1) {
                            return true;

                        } else if (VaccineModel.getLocality().toLowerCase().indexOf(searchKeyword) > -1) {
                            return true;

                        } else if (VaccineModel.getDate().toLowerCase().indexOf(searchKeyword) > -1) {
                            return true;

                        }else {
                            return false;//no match found
                        }
                    });
                });
                SortedList<VaccineModel> sortedVaccineTableData = new SortedList<>(filteredVaccineTableData);
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

    public void updateReportDetails(DocAdverseReactionTableModel adverseReactionInfo, ReportsTableModel reportInfo){
        setReportId(reportInfo.getReportId());
        System.out.println(reportInfo.getReportId());
        setDescription(adverseReactionInfo.getDescription());
        System.out.println(adverseReactionInfo.getDescription());
        setGravity(adverseReactionInfo.getGravityLevel());
        System.out.println(adverseReactionInfo.getGravityLevel());
        setDoctorId(reportInfo.getDoctorId());
        System.out.println(reportInfo.getDoctorId());
        setAdReactionDate(reportInfo.getAdReactionDate());
        System.out.println(reportInfo.getAdReactionDate());
    }

    public Text getReportId() {
        return reportId;
    }

    public TextArea getDescription() {
        return description;
    }

    public Text getDoctorId() {
        return doctorId;
    }

    public Text getGravity() {
        return gravity;
    }

    public Text getAdReactionDate() {
        return adReactionDate;
    }

    public void setReportId(String reportId) {
        this.reportId.setText(reportId);
    }

    public void setDescription(String description) {
        this.description.setText(description);
    }

    public void setDoctorId(String doctorId) {
        this.doctorId.setText(doctorId);
    }

    public void setGravity(Integer gravity) {
        this.gravity.setText(gravity.toString());
    }

    public void setAdReactionDate(String adReactionDate) {
        this.adReactionDate.setText(adReactionDate);
    }
}
