package com.vaxreact;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ReportForVaxController extends FarmaController implements Initializable {

    @FXML
    private PieChart pieChart;

    @FXML
    private TableView<TableModel> reportsForVaxTable;

    @FXML
    private TableColumn<TableModel, String> vaccineColumn;

    @FXML
    private TableColumn<TableModel, String> numOfReportsColumn;

    @FXML
    private TextField reportsForVaxTextField;

    ObservableList<TableModel> reportsForVaxTableModel = FXCollections.observableArrayList();

    private ArrayList<ArrayList<String>> vaccinesList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            updateTables();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //updatePiechart();

        createPiechart();


        Platform.runLater(()-> {


        });
    }
    public ObservableList<PieChart.Data> updatePiechart(){


        TableModel item = reportsForVaxTable.getItems().get(0);

        TableColumn col = reportsForVaxTable.getColumns().get(0);

        // this gives the value in the selected cell:
        String data = (String) col.getCellObservableValue(item).getValue();
        System.out.println("data: "+data);
        pieChart.getData().addAll(createPiechart());
        return createPiechart();
    }

    public ObservableList<PieChart.Data> createPiechart(){
        VaccineModel vaccineModel = new VaccineModel();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data(vaccineModel.getAstrazeneca(), 0),
                new PieChart.Data(vaccineModel.getModerna(), 75),
                new PieChart.Data(vaccineModel.getPfizer(), 25),
                new PieChart.Data(vaccineModel.getSputnik(), 0),
                new PieChart.Data(vaccineModel.getSinovac(), 0),
                new PieChart.Data(vaccineModel.getAntiinfluenzale(), 0));

        return pieChartData;


    }

    @Override
    public void updateTables() throws SQLException {
        ArrayList<TableColumn<TableModel,String>> arrayListReportsForVax = createReportForVaxTableObject();
        Query query = new Query();
        query.executeUpdate(reportsForVaxTableModel, reportsForVaxTable, reportsForVaxTextField, arrayListReportsForVax,6);
        System.out.println("1 fatto");
    }
    public ArrayList<TableColumn<TableModel,String>> createReportForVaxTableObject(){
        ArrayList<TableColumn<TableModel,String>> arrayList = new ArrayList<>();
        arrayList.add(vaccineColumn);
        arrayList.add(numOfReportsColumn);

        return arrayList;
    }
}
