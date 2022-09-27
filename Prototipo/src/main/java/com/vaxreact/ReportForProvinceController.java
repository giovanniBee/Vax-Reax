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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ReportForProvinceController extends FarmaController implements Initializable {


    @FXML
    private TableView<?> reportsForProvinceTable;

    @FXML
    private TextField reportsForProvinceTextField;

    @FXML
    private TableColumn<TableModel, String> provinceColumn;

    @FXML
    private TableColumn<TableModel, String> numOfReportsColumn;

    ObservableList<TableModel> reportsForProvinceTableModel = FXCollections.observableArrayList();

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
        ArrayList<TableColumn<TableModel,String>> arrayListReportsForProvince = createReportForProvinceTableObject();
        Query query = new Query();
        query.executeUpdate(reportsForProvinceTableModel, reportsForProvinceTable, reportsForProvinceTextField, arrayListReportsForProvince,8);
        System.out.println("1 fatto");
    }
    public ArrayList<TableColumn<TableModel,String>> createReportForProvinceTableObject(){
        ArrayList<TableColumn<TableModel,String>> arrayList = new ArrayList<>();
        arrayList.add(provinceColumn);
        arrayList.add(numOfReportsColumn);

        return arrayList;
    }
}
