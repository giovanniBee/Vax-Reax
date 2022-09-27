package com.vaxreact;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


public class VaccineModel {
    private String astrazeneca, pfizer, moderna, sputnik, sinovac, antiinfluenzale;
    public VaccineModel() {
        this.astrazeneca = "Astrazeneca";
        this.pfizer = "Pfizer";
        this.moderna = "Moderna";
        this.sputnik = "Sputnik";
        this.sinovac = "Sinovac";
        this.antiinfluenzale = "Antiinfluenzale";
    }

    public String getAstrazeneca() {
        return astrazeneca;
    }

    public String getPfizer() {
        return pfizer;
    }

    public String getModerna() {
        return moderna;
    }

    public String getSputnik() {
        return sputnik;
    }

    //permette di convertire a stringa l'oggetto
    public String[] toStringVector() {
        String[] objToVector = new String[6];
        objToVector[0]=this.astrazeneca;
        objToVector[1]=this.pfizer;
        objToVector[2]=this.moderna;
        objToVector[3]=this.sputnik;
        objToVector[4]=this.sinovac;
        objToVector[5]=this.antiinfluenzale;
        return objToVector;
    }

    public String getSinovac() {
        return sinovac;
    }

    public String getAntiinfluenzale() {
        return antiinfluenzale;
    }
}
