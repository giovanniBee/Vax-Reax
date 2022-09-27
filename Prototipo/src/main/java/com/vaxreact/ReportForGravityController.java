package com.vaxreact;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ReportForGravityController extends FarmaController implements Initializable {


    @FXML
    private TableColumn<TableModel, String> dataReportColumn;

    @FXML
    private TableColumn<TableModel, String> gravityColumn;

    @FXML
    private TableColumn<TableModel, String> countColumn;
    @FXML
    private PieChart pieChart;

    @FXML
    private TableColumn<TableModel, String> reportColumn;

    @FXML
    private TableView<TableModel> reportsForGravityTable;

    @FXML
    private TextField reportsForGravityTextField;



    ObservableList<TableModel>reportsForGravityTableModel = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            updateTables();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateTables() throws SQLException {
        ArrayList<TableColumn<TableModel,String>> arrayListReportsForGravity = createReportForGravityTableObject();
        Query query = new Query();
        query.executeUpdate(reportsForGravityTableModel, reportsForGravityTable, reportsForGravityTextField, arrayListReportsForGravity,7);
    }
    public ArrayList<TableColumn<TableModel,String>> createReportForGravityTableObject(){
        ArrayList<TableColumn<TableModel,String>> arrayList = new ArrayList<>();
        arrayList.add(gravityColumn);
        arrayList.add(countColumn);
        return arrayList;
    }
}
