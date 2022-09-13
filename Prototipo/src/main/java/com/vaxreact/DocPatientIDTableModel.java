package com.vaxreact;

public class DocPatientIDTableModel {

    String patientId;

    public DocPatientIDTableModel(String patientID) {
        patientId = patientID;
    }

    public String getPatientID() {
        return patientId;
    }

    public void setPatientID(String patientID) {
        patientId = patientID;
    }
}
