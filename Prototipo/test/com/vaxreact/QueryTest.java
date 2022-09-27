package com.vaxreact;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.junit.jupiter.api.Test;
import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class QueryTest {

    @Test
    void testGetCell() {
        String query = "SELECT \"Descrizione\" FROM \"Segnalazione\" WHERE \"Codice\" = '8'";
        //Query tester = new Query();

        assertEquals("Forte tosse",Query.getCell(query));
        assertNotNull(Query.getCell(query));
        assertNotEquals("Il paziente ha un dolore allo stomaco.",Query.getCell(query));
    }

    @Test
    void testExecuteNewInsert() throws SQLException {
        String insertQuery = "INSERT INTO \"Segnalazione\"(\"Codice\", \"DataReport\", \"ReazioneAvversa\", \"CodicePaziente\", \"UserMedico\", \"DataReazione\", \"Gravita\", \"Descrizione\") " +
                "VALUES ('6', DEFAULT, 'Diarrea' ,'1','asa','2022-09-17', '2', 'Il paziente ha forti crampi allo stomaco')";


        assertFalse(Query.executeNewInsert(insertQuery));
    }

    @Test
    void testGetRowValue() throws SQLException {

        String query = "SELECT \"Nome\" FROM \"ReazioneAvversa\"";
        Query query1 = new Query();

        Connection connectDB = CreateConnection.getInstance().getConnection();
        Statement statement = connectDB.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet queryOutput = statement.executeQuery(query);

        assert queryOutput != null;
        if (!queryOutput.next()) System.exit(0);
        queryOutput.next();

        ArrayList<String> s1 = new ArrayList<>();

        s1.add(queryOutput.getString(1));

        assertEquals(s1, query1.getRowValue(queryOutput));

        assert queryOutput != null;
        if (!queryOutput.next()) System.exit(0);
        queryOutput.next();

        ArrayList<String> s2 = new ArrayList<>();
        s2.add(queryOutput.getString(1));

        assertEquals(s2, query1.getRowValue(queryOutput));

        assert queryOutput != null;
        if (!queryOutput.next()) System.exit(0);

        queryOutput.next();
        ArrayList<String> s3 = new ArrayList<>();
        s3.add(queryOutput.getString(1));

        assertEquals(s3, query1.getRowValue(queryOutput));

    }

    @Test
    void testSetIntoCell() throws SQLException  {

        String string = "PazienteZero";

        TableColumn<TableModel,String> column = new TableColumn<>();
        column.setText(string);

        ArrayList<TableColumn<TableModel,String>> arrayListTest = new ArrayList<>();
        arrayListTest.add(column);

        String query = "SELECT \"Nome\" FROM \"ReazioneAvversa\"";
        Connection connectDB = CreateConnection.getInstance().getConnection();
        Statement statement = connectDB.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet queryOutput = statement.executeQuery(query);

        assert queryOutput != null;
        if (!queryOutput.next()) System.out.println("Non ci sono più righe da leggere");System.exit(0);
        queryOutput.next();

        Query querY = new Query();

        assertFalse(arrayListTest.isEmpty());
        assertNotNull(queryOutput);

        querY.setIntoCell(arrayListTest,queryOutput);

        assertEquals("PazienteZero",arrayListTest.get(0).getText());

    }
}