package com.vaxreact;

import java.sql.Date;
import java.util.Calendar;

public class DocModel {

    String patientID;
    String riskFactor;
    String adverseReaction;
    Date date;
    // long miliseconds = System.currentTimeMillis();
   // Date date = new Date(miliseconds);


    //Constructor
    public DocModel(String patientID, String riskFactor, String adverseReaction, Date date) {
        this.patientID = patientID;
        this.riskFactor = riskFactor;
        this.adverseReaction = adverseReaction;
        this.date = date;
    }

    //GETTERS
    public String getPatientID() {
        return patientID;
    }

    public String getRiskFactor() {
        return riskFactor;
    }

    public String getAdverseReaction() {
        return adverseReaction;
    }

    public Date getDate() {
        return date;
    }

    //SETTERS
    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public void setRiskFactor(String riskFactor) {
        this.riskFactor = riskFactor;
    }

    public void setAdverseReaction(String adverseReaction) {
        this.adverseReaction = adverseReaction;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
