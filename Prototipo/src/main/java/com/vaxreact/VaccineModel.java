package com.vaxreact;

public class VaccineModel {
    String name,type,locality,date;

    public VaccineModel(String name, String type, String locality, String date) {
        this.name = name;
        this.type = type;
        this.locality = locality;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getLocality() {
        return locality;
    }

    public String getDate() {
        return date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
