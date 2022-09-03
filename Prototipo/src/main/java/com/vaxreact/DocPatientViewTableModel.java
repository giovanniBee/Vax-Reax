package com.vaxreact;

public class DocPatientViewTableModel {
    String reportId;
    String patientId;
    String adverseReaction;
    String adReactionDate;
    String reportDate;

    public DocPatientViewTableModel(String reportId, String patientId, String adverseReaction, String adReactionDate, String reportDate) {
        this.reportId = reportId;
        this.patientId = patientId;
        this.adverseReaction = adverseReaction;
        this.adReactionDate = adReactionDate;
        this.reportDate = reportDate;
    }

    public String getReportId() {
        return reportId;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getAdverseReaction() {
        return adverseReaction;
    }

    public String getAdReactionDate() {
        return adReactionDate;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public void setAdverseReaction(String adverseReaction) {
        this.adverseReaction = adverseReaction;
    }

    public void setAdReactionDate(String adReactionDate) {
        this.adReactionDate = adReactionDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }
}