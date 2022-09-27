package com.vaxreact;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.postgresql.util.PSQLException;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class PatientInfoDetailsController extends DocController implements Initializable {

    @FXML
    private Text patientIdText;

    @FXML
    private AnchorPane patientInfoStage;

    @FXML
    private TextField riskFactorDataTextField;

    @FXML
    private TableColumn<TableModel, String> riskFactorLevel;

    @FXML
    private TableColumn<TableModel, String> riskFactorName;

    @FXML
    private TableView<TableModel> riskFactorsTable;

    @FXML
    private TableColumn<TableModel, String> vaccineData;

    @FXML
    private TextField vaccineDataTextField;

    @FXML
    private TableColumn<TableModel, String> vaccineName;

    @FXML
    private TableColumn<TableModel, String> vaccineSite;

    @FXML
    private TableColumn<TableModel, String> vaccineType;

    @FXML
    private TableView<TableModel> vaccinesHistoryTable;

    ObservableList<TableModel> docRiskFactorsTableModelObservableList = FXCollections.observableArrayList();
    ObservableList<TableModel> docVaccineHistoryTableModelObservableList = FXCollections.observableArrayList();

    String patientId;



    public void setPatientsInfoDetails(String patientId){
        this.patientId = patientId;
    }
    public String getPatientsInfoDetails(){
        return this.patientId;
    }

    public String getRiskFactorsQuery(String patientId){
        return "SELECT PF.\"nomeFattore\",F.\"LivelloDiRischio\"\n" +
                "FROM \"Paziente_FattoreRischio\" PF,\"FattoreDiRischio\" F\n" +
                "WHERE PF.\"nomeFattore\" = F.\"Nome\" AND \"codicePaziente\" = '"+ patientId +"'";
    }
    public String getVaccineHistory(String patientId){
        return "SELECT \"Nome\", \"Tipo\",\"Sede\",\"Data\"\n" +
                "FROM \"Vaccinazione\"\n" +
                "WHERE \"CodicePaziente\" = '"+ patientId +"'";
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Platform.runLater(() -> {
            initializeView(getPatientsInfoDetails());
            updateTables();
        });
    }

    @Override
    public void updateTables(){
                executeUpdate(docRiskFactorsTableModelObservableList,riskFactorsTable,riskFactorDataTextField,createRiskFactorsTableObject(),1);
                System.out.println("Fatto1");
                executeUpdate(docVaccineHistoryTableModelObservableList,vaccinesHistoryTable,vaccineDataTextField,createVaccinesHistoryTableObject(),2);
        System.out.println("Fatto2");
    }
    private void executeUpdate(ObservableList tableModelObservableList, TableView table, TextField textField, ArrayList<TableColumn<TableModel,String>> arrayList, int flag) {
        tableModelObservableList.clear();

        Connection connectDB = CreateConnection.getInstance().getConnection();
        try {
            Statement statement = connectDB.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet queryOutput = null;
            if(flag == 1){
                queryOutput = statement.executeQuery(getRiskFactorsQuery(getPatientsInfoDetails()));
            }else if (flag == 2){
                queryOutput = statement.executeQuery(getVaccineHistory(getPatientsInfoDetails()));
            }

            Query query = new Query();
            while (true) {
                assert queryOutput != null;
                if (!queryOutput.next()) break;

                //Popolazione ObservableList
                tableModelObservableList.add(new TableModel(query.getRowValue(queryOutput)));
            }

            query.setIntoCell(arrayList, queryOutput);
            System.out.println("fuori da setIntoCell");

            table.setItems(tableModelObservableList);

            FilteredList<TableModel> filteredTableData = new FilteredList<>(tableModelObservableList, b -> true);

            //ResultSet finalQueryOutput = queryOutput;
            textField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredTableData.setPredicate(TableModel -> {

                    if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                        return true; //C'è un match
                    }
                    String searchKeyword = newValue.toLowerCase();

                    if (TableModel.getNomeFattore().toLowerCase().indexOf(searchKeyword) > -1 ) {
                        return true;
                    } else if(TableModel.getLivelloDiRischio().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;
                    } else if(TableModel.getNome().toLowerCase().indexOf(searchKeyword) > -1){
                        return  true;
                    }else if(TableModel.getTipo().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    } else if( flag == 2 && arrayList.get(2).getText().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    } else if( flag == 2 && arrayList.get(3).getText().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    }
                    else
                        return false;//no match found
                });
            });
            SortedList<TableModel> sortedTableData = new SortedList<>(filteredTableData);
            sortedTableData.comparatorProperty().bind(table.comparatorProperty());
            table.setItems(sortedTableData);

        } catch (PSQLException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void initializeView(String patientId){
        patientIdText.setText(patientId);
    }

    public ArrayList<TableColumn<TableModel,String>> createRiskFactorsTableObject(){
        ArrayList<TableColumn<TableModel,String>> arrayList = new ArrayList<>();
        arrayList.add(riskFactorName);
        arrayList.add(riskFactorLevel);
        return arrayList;
    }

    public ArrayList<TableColumn<TableModel,String>> createVaccinesHistoryTableObject(){
        ArrayList<TableColumn<TableModel,String>> arrayList = new ArrayList<>();
        arrayList.add(vaccineName);
        arrayList.add(vaccineType);
        arrayList.add(vaccineSite);
        arrayList.add(vaccineData);
        return arrayList;
    }

}
