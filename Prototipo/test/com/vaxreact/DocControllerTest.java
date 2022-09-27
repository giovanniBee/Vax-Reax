package com.vaxreact;

import javafx.scene.control.TableColumn;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DocControllerTest {

    @Test
    void testCreateReportsTableObject() {

        String string = "PazienteZero";

        TableColumn<TableModel,String> column = new TableColumn<>();
        column = null;

        ArrayList<TableColumn<TableModel,String>> arrayListTest = new ArrayList<>();
        DocController docController = new DocController();

        int MAX_VALUE = docController.createReportsTableObject().size();

        for(int i = 0; i < MAX_VALUE ; i++)
        arrayListTest.add(column);

        assertEquals(arrayListTest,docController.createReportsTableObject());

        assertNotNull(docController.createReportsTableObject());

    }

    @Test
    void testCreatePatientInfoTableObject() {
        String string = "PazienteZero";

        TableColumn<TableModel,String> column = new TableColumn<>();
        column = null;

        ArrayList<TableColumn<TableModel,String>> arrayListTest = new ArrayList<>();
        DocController docController = new DocController();
        int MAX_VALUE = docController.createPatientInfoTableObject().size();
        for(int i = 0; i < MAX_VALUE ; i++)
            arrayListTest.add(column);

        assertEquals(arrayListTest,docController.createPatientInfoTableObject());
        assertNotNull(docController.createPatientInfoTableObject());

    }


    @Test
    void testCreatePatientIDTableObject() {
        String string = "PazienteZero";


        TableColumn<TableModel,String> column = new TableColumn<>();
        column = null;

        ArrayList<TableColumn<TableModel,String>> arrayListTest = new ArrayList<>();
        DocController docController = new DocController();
        int MAX_VALUE = docController.createPatientIDTableObject().size();
        for(int i = 0; i < MAX_VALUE ; i++)
            arrayListTest.add(column);

        assertEquals(arrayListTest,docController.createPatientIDTableObject());
        assertNotNull(docController.createPatientIDTableObject());
    }

    @Test
    void testCreateAdverseReactionTableObject() {
        String string = "PazienteZero";


        TableColumn<TableModel,String> column = new TableColumn<>();
        column = null;

        ArrayList<TableColumn<TableModel,String>> arrayListTest = new ArrayList<>();
        DocController docController = new DocController();
        int MAX_VALUE = docController.createAdverseReactionTableObject().size();
        for(int i = 0; i < MAX_VALUE ; i++)
            arrayListTest.add(column);

        assertEquals(arrayListTest,docController.createAdverseReactionTableObject());
        assertNotNull(docController.createAdverseReactionTableObject());
    }
}