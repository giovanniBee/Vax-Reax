package com.vaxreact;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.postgresql.util.PSQLException;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Query {


    public static String preparedLogin() {return "SELECT \"Utente\".\"Professione\" FROM \"Utente\" WHERE \"Username\" = ?  AND \"Password\" = ?";}
    public static String getReportsQuery(){
            return "SELECT * FROM \"Segnalazione\" ORDER BY \"Codice\" ASC";
    }

    public static String getPatientIDQuery(){
        return "SELECT \"Codice\" FROM \"Paziente\" ORDER BY \"Codice\" ASC ";
    }

    public static String getAdReactionQuery(){
        return "SELECT * FROM \"ReazioneAvversa\" ORDER BY \"Nome\" ASC ";
    }

    public static String getPatientInfoQuery(){
        return """
                SELECT DISTINCT(P."Codice"), "Anno", "Residenza", "Professione", COUNT(S."Codice") AS "NumReazioni"
                FROM "Paziente" P, "Segnalazione" S
                WHERE P."Codice" = S."CodicePaziente"\s
                GROUP BY P."Codice"\s
                ORDER BY P."Codice" ASC""";
    }

    public static String getDescriptionQuery(String reportId){
        return "SELECT \"Descrizione\" FROM \"Segnalazione\" WHERE \"Codice\" = '"+ reportId +"'";
    }
    public static String getGravityQuery(String reportId){
        return "SELECT \"Gravita\" FROM \"Segnalazione\" WHERE \"Codice\" = '"+ reportId +"'";
    }
    public String getReportsForVax(){
        return """
                SELECT VS."Vaccinazione_Nome" AS "NomeVaccinazione", COUNT(VS."Vaccinazione_Nome") AS "ReportsPerVaccino"
                FROM "Vaccinazione_Segnalazione" VS
                GROUP BY VS."Vaccinazione_Nome"
                ORDER BY VS."Vaccinazione_Nome\"""";
    }

    public String getReportsForGravity(){
        return """
                SELECT "Gravita" AS "LivelloGravita",COUNT("Segnalazione".*)
                FROM "Segnalazione"
                WHERE "Gravita" >= 3 AND "DataReport" BETWEEN (CURRENT_DATE - INTERVAL '1 week') AND CURRENT_DATE
                GROUP BY "Gravita\"""";
    }
    public String getReportsForSite(){
        return """
                SELECT "Sede" AS "Site", COUNT("Segnalazione".*) AS "SegnalazioniPerSede"
                FROM "Sede" SE, "Vaccinazione", "Vaccinazione_Segnalazione", "Segnalazione"
                WHERE "Vaccinazione_Segnalazione"."Vaccinazione_Nome" = "Vaccinazione"."Nome" AND
                "Vaccinazione_Segnalazione"."Vaccinazione_Tipo" = "Vaccinazione"."Tipo" AND
                "Vaccinazione_Segnalazione"."Vaccinazione_Codice" = "Vaccinazione"."CodicePaziente" AND
                "Vaccinazione_Segnalazione"."Segnalazione_Codice"= "Segnalazione"."Codice" AND
                SE."Nome" = "Vaccinazione"."Sede"
                GROUP BY "Sede"
                ORDER BY "Sede\"""";
    }
    public String getReportsForProvince(){
        return """
                SELECT SE."Provincia", COUNT("Segnalazione".*) AS "SegnalazioniPerProvincia"
                FROM "Sede" SE, "Vaccinazione", "Vaccinazione_Segnalazione", "Segnalazione"
                WHERE "Vaccinazione_Segnalazione"."Vaccinazione_Nome" = "Vaccinazione"."Nome" AND
                "Vaccinazione_Segnalazione"."Vaccinazione_Tipo" = "Vaccinazione"."Tipo" AND
                "Vaccinazione_Segnalazione"."Vaccinazione_Codice" = "Vaccinazione"."CodicePaziente" AND
                "Vaccinazione_Segnalazione"."Segnalazione_Codice"= "Segnalazione"."Codice" AND
                SE."Nome" = "Vaccinazione"."Sede"GROUP BY SE."Provincia\"""";
    }
    public static String getVerificationInTheLastMonthQuery(Integer reportId){
        return "SELECT V.\"Nome\",V.\"Tipo\", V.\"CodicePaziente\",S.\"Codice\",S.\"Gravita\"\n" +
                "FROM \"Vaccinazione\" V, \"Segnalazione\" S\n" +
                "WHERE V.\"CodicePaziente\" = S.\"CodicePaziente\" AND S.\"Codice\" = '"+  reportId +"' AND S.\"Gravita\" >= 3\n" +
                "AND V.\"Data\" BETWEEN (S.\"DataReazione\" - INTERVAL '1 month') AND S.\"DataReazione\"";
    }
    public static String getControlQuery(String vaccino){
        return "SELECT COUNT(VS.\"Vaccinazione_Nome\")\n" +
                "FROM \"Vaccinazione_Segnalazione\"VS\n" +
                "WHERE \"Vaccinazione_Nome\" = '"+  vaccino +"'";
    }

    //
    public static String getNReports(){
        return  "SELECT COUNT (\"Segnalazione\".\"Codice\") AS N FROM \"Segnalazione\"";
    }
    public static String getLastInsertQuery() {
        return"SELECT MAX(\"Codice\") FROM \"Segnalazione\"";
    }
    public static String getCell(String query){
        String cell = null;
        Connection connectDB = CreateConnection.getInstance().getConnection();
        try {
            Statement statement = connectDB.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet queryOutput = statement.executeQuery(query);

            while (true) {
                assert queryOutput != null;
                if (!queryOutput.next()) break;
                cell = queryOutput.getString(1);
                //System.out.println(queryOutput.getString(1));
            }
        } catch (PSQLException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cell;
    }

    public void executeUpdate(ObservableList tableModelObservableList, TableView table, TextField textField, ArrayList<TableColumn<TableModel,String>> arrayList, int flag) throws SQLException {
        tableModelObservableList.clear();

        Connection connectDB = CreateConnection.getInstance().getConnection();
        Statement statement = connectDB.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet queryOutput = null;
        try {

            if (flag == 1) {
                queryOutput = statement.executeQuery(getReportsQuery());
            } else if (flag == 2) {
                queryOutput = statement.executeQuery(getPatientIDQuery());
            } else if (flag == 3) {
                queryOutput = statement.executeQuery(getAdReactionQuery());
            } else if (flag == 4) {
                queryOutput = statement.executeQuery(getPatientInfoQuery());
            } else if (flag == 5) {
                queryOutput = statement.executeQuery(getReportsQuery());
            } else if (flag == 6) {
                queryOutput = statement.executeQuery(getReportsForVax());
            } else if (flag == 7) {
                queryOutput = statement.executeQuery(getReportsForGravity());
            } else if (flag == 8) {
                queryOutput = statement.executeQuery(getReportsForProvince());
            } else if (flag == 9) {
                queryOutput = statement.executeQuery(getReportsForSite());
            }

            while (true) {
                assert queryOutput != null;
                if (!queryOutput.next()) break;

                //Popolazione ObservableList
                tableModelObservableList.add(new TableModel(getRowValue(queryOutput)));
            }


            setIntoCell(arrayList, queryOutput);
            table.setItems(tableModelObservableList);

            FilteredList<TableModel> filteredTableData = new FilteredList<>(tableModelObservableList, b -> true);

            textField.textProperty().addListener((observable, oldValue, newValue) -> filteredTableData.setPredicate(TableModel -> {

                if (newValue.isEmpty() || newValue.isBlank()) {
                    return true; //C'è un match
                }
                String searchKeyword = newValue.toLowerCase();


                if (TableModel.getCodice().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (TableModel.getNome().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if ((flag == 4 || flag == 1) && TableModel.getDataReazione().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if ((flag == 4 || flag == 1) && TableModel.getAnno().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if ((flag == 4 || flag == 1) && TableModel.getReazioneAvversa().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if ((flag == 4 || flag == 1) && TableModel.getResidenza().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if ((flag == 4 || flag == 1) && TableModel.getCodicePaziente().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if ((flag == 4 || flag == 1) && TableModel.getProfessione().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if ((flag == 4 || flag == 1) && TableModel.getUserMedico().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if ((flag == 4 || flag == 1) && TableModel.getNumReazioni().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (flag == 1 && TableModel.getDataReport().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (flag == 1 && TableModel.getGravita().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else return flag == 1 && arrayList.get(7).getText().toLowerCase().contains(searchKeyword);
                //}
            }));
            SortedList<TableModel> sortedTableData = new SortedList<>(filteredTableData);
            sortedTableData.comparatorProperty().bind(table.comparatorProperty());
            table.setItems(sortedTableData);

        } catch (PSQLException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connectDB.close();
            } catch (Exception e) { /* Ignored */ }
            try {
                statement.close();
            } catch (Exception e) { /* Ignored */ }
        }
    }

    public static String insertControlPhase(String nome, String userFarma, String descrizione){
        return "INSERT INTO public.\"PropostaFarmacologo\"(\n" +
                "\t\"Codice\", \"Nome\", \"UserFarmacologo\", \"Data\", \"Descrizione\")\n" +
                "\tVALUES (DEFAULT,'"+  nome +"','"+  userFarma +"',DEFAULT,'"+  descrizione +"')";
    }

    public static String insertVaccination_ReportQuery(String vaccino, String codiceReport, String tipo, String paziente){
        return "INSERT INTO public.\"Vaccinazione_Segnalazione\"(\n" +
                "\t\"Vaccinazione_Nome\", \"Segnalazione_Codice\", \"Vaccinazione_Tipo\", \"Vaccinazione_Codice\")\n" +
                "\tVALUES ('"+vaccino  +"','"+codiceReport  +"','"+tipo  +"','"+paziente  +"')";
    }

    public static String insertQuery(String commitPatientID, String commitAdverseReaction, String commitAdReactionDate, String userInfo, Integer gravity, String description) {
        return "INSERT INTO \"Segnalazione\"(\"Codice\", \"DataReazione\", \"ReazioneAvversa\", \"CodicePaziente\", \"UserMedico\", \"DataReport\", \"Gravita\", \"Descrizione\") VALUES (DEFAULT, '" + commitAdReactionDate + "','" + commitAdverseReaction + "', '"+ commitPatientID +"', '" + userInfo + "' ,CURRENT_DATE, '"+ gravity +"', '"+ description +"')";
    }
    public static boolean executeNewInsert(String query) throws SQLException {
        Connection connectDB = CreateConnection.getInstance().getConnection();
        try (connectDB; PreparedStatement statement = connectDB.prepareStatement(query)) {
            //statement.executeUpdate(query);
            statement.executeUpdate();
        } catch (PSQLException e) {
            Logger.getLogger(DocController.class.getName()).log(Level.FINE, e.getMessage(), e);
            System.out.println(e.getMessage());
            return true;
        } catch (SQLException e) {
            Logger.getLogger(DocController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
        /* Ignored */
        /* Ignored */
        return false;
    }

    public static ArrayList<String> getRowValue( ResultSet queryOutput ) {
        System.out.println("getRowValue: ");
        ArrayList<String> column = new ArrayList<>();
        try {
            int totColumns = queryOutput.getMetaData().getColumnCount();
            //column.clear();
            for (int i = 0; i < totColumns ; i++) {
                //il parametro di getString è una stringa che riporta il nome esatto della colonna all'interno del Database su pSQL
                column.add(queryOutput.getString(i + 1));
                System.out.print(column.get(i)+" ("+i+"), ");
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
    public void setIntoCell( ArrayList<TableColumn<TableModel,String>> arrayList, ResultSet queryOutput ){
        String columnName;
        try {
            int totColumns = queryOutput.getMetaData().getColumnCount();
            System.out.println("totColumn: "+totColumns);
            for (int i = 0; i < totColumns; i++) {
                columnName = queryOutput.getMetaData().getColumnName(i+1);
                System.out.println("colonna:"+ columnName +", i:"+ i +", arrayList.get("+ i +"):" + arrayList.get(i));
                arrayList.get(i).setCellValueFactory(new PropertyValueFactory<>(columnName));
                System.out.println();
            }
            System.out.println("Fine ciclo for");
        }
        catch (PSQLException e){
            System.out.println(e.getMessage());
        }catch (SQLException e){
            e.printStackTrace();
        }
    }


}
