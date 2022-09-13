package com.vaxreact;

import java.util.ArrayList;

public class ReportsTableModel {
    ArrayList<String> arrayList = new ArrayList<>();
    /*
    String reportId;
    String patientId;
    String adverseReaction;
    String adReactionDate;
    String reportDate;
    String doctorId;
    */
    public ReportsTableModel(ArrayList<String> arrList) {

        this.arrayList = arrList;

    }

    public String getReportId() {
        return this.arrayList.get(0);
    }

    public String getPatientId() {
        return this.arrayList.get(1);
    }

    public String getAdverseReaction() {
        return this.arrayList.get(2);
    }

    public String getAdReactionDate() {
        return this.arrayList.get(3);
    }

    public String getReportDate() {
        return this.arrayList.get(4);
    }

    public String getDoctorId() {
        return this.arrayList.get(5);
    }

    public void setReportId(String reportId) {
        this.arrayList.set(0,reportId);
    }

    public void setPatientId(String patientId) {
        this.arrayList.set(1,patientId);
    }

    public void setAdverseReaction(String adverseReaction) {
        this.arrayList.set(2,adverseReaction);
    }

    public void setAdReactionDate(String adReactionDate) {
        this.arrayList.set(3,adReactionDate);
    }

    public void setReportDate(String reportDate) {
        this.arrayList.set(4,reportDate);
    }

    public void setDoctorId(String doctorId) {
        this.arrayList.set(5,doctorId);
    }
}