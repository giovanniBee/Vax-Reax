package com.vaxreact;

import java.util.ArrayList;

public class TableModel {

    ArrayList<String> arrayList = new ArrayList<>();

    public TableModel() {
    }

    public TableModel(ArrayList<String> arrList) {
        this.arrayList = arrList;
    }


    public String getReportId(){return arrayList.get(0);}
    public String getReportDate(){return arrayList.get(1);}
    public String getAdverseReaction(){return arrayList.get(2);}
    public String getPatientId(){return arrayList.get(3);}
    public String getDoctorId(){return arrayList.get(4);}
    public String getAdReactionDate(){return arrayList.get(5);}


    //public String getGravity(){return arrayList.get(6);}
    //public String getDescription(){return arrayList.get(7);}

    //TABELLA SEGNALAZIONE
    public String getCodice(){return arrayList.get(0);}
    public String getDataReazione(){return arrayList.get(1);}
    public String getReazioneAvversa(){return arrayList.get(2);}
    public String getCodicePaziente(){return arrayList.get(3);}
    public String getUserMedico(){return arrayList.get(4);}
    public String getDataReport(){return arrayList.get(5);}
    public String getGravita(){return arrayList.get(6);}
    public String getDescrizione(){return arrayList.get(7);}


    public String getId(){return arrayList.get(0);}
    public String getAnnoNascita(){return arrayList.get(1);}
    public String getResidenza(){return arrayList.get(2);}
    public String getProfessione(){return arrayList.get(3);}
    public String getFattoriRischio(){return arrayList.get(4);}
    public String getStoriaVaccini(){return arrayList.get(5);}
    public String getReazioniAvverse(){return arrayList.get(6);}
    public String getNumVaccini(){return arrayList.get(7);}


    //TABELLA REAZIONE AVVERSA
    public String getNome(){return arrayList.get(0);}

    //TABELLA PATIENTSINFO
    public String getAnno(){return arrayList.get(1);}
    public String getNumReazioni(){return arrayList.get(4);}

    //patientInfoDetailsController - TABELLA FATTORI DI RISCHIO
    public String getNomeFattore(){return arrayList.get(0);}
    public String getLivelloDiRischio(){return arrayList.get(1);}

    //patientInfoDetailsController - TABELLA VACCINI

    //public String getNome(){return arrayList(0);}
    public String getTipo(){return arrayList.get(1);}
    public String getSede(){return arrayList.get(2);}
    public String getData(){return arrayList.get(3);}

    //ReportForVaxController
    public String getNomeVaccinazione(){return arrayList.get(0);}
    public String getReportsPerVaccino(){return arrayList.get(1);}

    //ReportForGravityController
    public String getLivelloGravita(){return arrayList.get(0);}
    public String getCount(){return arrayList.get(1);}

    //ReportForProvinceController
    public String getProvincia(){return arrayList.get(0);}
    public String getSegnalazioniPerProvincia(){return arrayList.get(1);}


    //ReportsForSiteController
    public String getSite(){return arrayList.get(0);}
    public String getSegnalazioniPerSede(){return arrayList.get(1);}
    /*
    public String getNome(){return arrayList.get(0);}
    public String getGravity(){return arrayList.get(1);}
    public String getDescrizione(){return arrayList.get(2);}
    */
    public void setArrayList(ArrayList<String> arrayList) {
        this.arrayList = arrayList;
    }



    /*
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
    */
}