package com.vaxreact;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ReportForSiteController extends FarmaController implements Initializable {


    @FXML
    private TableView<?> reportsForSiteTable;

    @FXML
    private TextField reportsForSiteTextField;

    @FXML
    private TableColumn<TableModel, String> siteColumn;

    @FXML
    private TableColumn<TableModel, String> numOfReportsColumn;

    ObservableList<TableModel> reportsForSiteTableModel = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rs) {

        try {
            updateTables();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateTables() throws SQLException {
        ArrayList<TableColumn<TableModel,String>> arrayListReportsForSite = createReportForSiteTableObject();
        Query query = new Query();
        query.executeUpdate(reportsForSiteTableModel, reportsForSiteTable, reportsForSiteTextField, arrayListReportsForSite,9);
        System.out.println("1 fatto");
    }
    public ArrayList<TableColumn<TableModel,String>> createReportForSiteTableObject(){
        ArrayList<TableColumn<TableModel,String>> arrayList = new ArrayList<>();
        arrayList.add(siteColumn);
        arrayList.add(numOfReportsColumn);

        return arrayList;
    }
}
