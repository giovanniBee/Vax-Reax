package com.vaxreact;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Query {

    public static String updateReportsQuery(){
        return "SELECT * FROM \"Doctor\".segnalazione\n" +
                "ORDER BY \"reportId\" ASC ";
    }
    public static void executeReportsUpdate(ObservableList reportsTableModelObservableList, TableView patientViewTable, TextField patientViewTextField) {
        reportsTableModelObservableList.clear();


        Connection connectDB = CreateConnection.getInstance().getConnection();
        try {
            Statement statement = connectDB.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet queryOutput = statement.executeQuery(updateReportsQuery());
            System.out.println(queryOutput.getMetaData().getColumnName(1));
            while (queryOutput.next()) {
                //Popolazione ObservableList
                reportsTableModelObservableList.add(new ReportsTableModel(Query.getRowValue(queryOutput)));
            }

            setIntoCell(queryOutput);
            /*
            reportIdColumn.setCellValueFactory(new PropertyValueFactory<>("reportId"));
            patientIdPatientViewColumn.setCellValueFactory(new PropertyValueFactory<>("patientId"));
            adverseReactionPatientViewColumn.setCellValueFactory(new PropertyValueFactory<>("adverseReaction"));
            adverseReactionDateColumn.setCellValueFactory(new PropertyValueFactory<>("adReactionDate"));
            reportDateColumn.setCellValueFactory(new PropertyValueFactory<>("reportDate"));
            doctorIdColumn.setCellValueFactory(new  PropertyValueFactory<>("doctorId"));
            */
            patientViewTable.setItems(reportsTableModelObservableList);

            FilteredList<ReportsTableModel> filteredPatientViewTableData = new FilteredList<>(reportsTableModelObservableList, b -> true);

            patientViewTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredPatientViewTableData.setPredicate(ReportsTableModel -> {
                    if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                        return true; //C'è un match
                    }
                    String searchKeyword = newValue.toLowerCase();
                    if (ReportsTableModel.getReportId().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;
                    } else if (ReportsTableModel.getPatientId().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;

                    } else if (ReportsTableModel.getAdverseReaction().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;

                    } else if (ReportsTableModel.getAdReactionDate().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;

                    } else if (ReportsTableModel.getReportDate().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;

                    }else if (ReportsTableModel.getDoctorId().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    } else {
                        return false;//no match found
                    }
                });
            });
            SortedList<ReportsTableModel> sortedPatientViewTableData = new SortedList<>(filteredPatientViewTableData);
            sortedPatientViewTableData.comparatorProperty().bind(patientViewTable.comparatorProperty());
            patientViewTable.setItems(sortedPatientViewTableData);

        }catch (PSQLException e){
            System.out.println(e.getMessage());
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static String insertQuery(String commitPatientID, String commitAdverseReaction, String commitAdReactionDate, String userInfo) {
        return "INSERT INTO \"Doctor\".segnalazione(\"reportId\", \"patientId\", \"adverseReaction\", \"adReactionDate\", \"reportDate\", \"doctorId\") VALUES (DEFAULT, '" + commitPatientID + "', '" + commitAdverseReaction + "', '" + commitAdReactionDate + "', CURRENT_DATE,'" + userInfo + "')";
    }
    public static  boolean executeNewInsert(String query){
        Connection connectDB = CreateConnection.getInstance().getConnection();
        try{
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(query);
        }catch (PSQLException e){
            Logger.getLogger(DocController.class.getName()).log(Level.FINE,e.getMessage(),e);
            System.out.println(e.getMessage());
            System.out.println("Qui2");
            return true;
        }catch (SQLException e){
            Logger.getLogger(DocController.class.getName()).log(Level.SEVERE,null, e);
            e.printStackTrace();
        }
        return false;
    }
    public static ArrayList<String> getRowValue(ResultSet queryOutput) {
        ArrayList<String> column = new ArrayList<>();
        try {
            int totColumns = queryOutput.getMetaData().getColumnCount();
            //column.clear();
            for (int i = 0; i < totColumns ; i++) {
                //il parametro di getString è una stringa che riporta il nome esatto della colonna all'interno del Database su pSQL
                column.add(queryOutput.getString(i + 1));
                //System.out.print(column.get(i)+" ("+i+"), ");
            }
            //System.out.println(column.get(totColumns-1)+" ("+(totColumns-1)+") ");
        }
        catch (PSQLException e){
        System.out.println(e.getMessage());
        }catch (SQLException e){
        e.printStackTrace();
        }
        return column;
    }

    public static void setIntoCell(ResultSet queryOutput ){

        String columnName = null;
        try {
            int totColumns = queryOutput.getMetaData().getColumnCount();
            for (int i = 0; i < totColumns; i++) {
                columnName = queryOutput.getMetaData().getColumnName(i+1);
                //arrList.get(i).setCellValueFactory(new PropertyValueFactory<>(columnName));
                DocController.reportIdColumn.setCellValueFactory((new PropertyValueFactory<>("reportId")));
            }
        }
        catch (PSQLException e){
            System.out.println(e.getMessage());
        }catch (SQLException e){
            e.printStackTrace();
        }
       // return arrList;
    }
    public static ArrayList<TableColumn<ReportsTableModel,String>> getReportsTableToArray(){
        ArrayList<TableColumn<ReportsTableModel,String>> arrayList = new ArrayList<>();
        arrayList.add(DocController.reportIdColumn);
        arrayList.add(DocController.patientIdReportsColumn);
        arrayList.add(DocController.adverseReactionReportsColumn);
        arrayList.add(DocController.adverseReactionDateColumn);
        arrayList.add(DocController.reportDateColumn);
        arrayList.add(DocController.doctorIdColumn);
        return arrayList;

    }

}
