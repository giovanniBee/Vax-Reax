package com.vaxreact;

public class VaccinationModel {
    String name,type, site,city,date;

    public VaccinationModel(String name, String type, String site, String city, String date) {
        this.name = name;
        this.type = type;
        this.site = site;
        this.city = city;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getSite() {
        return site;
    }

    public String getCity() {
        return city;
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

    public void setSite(String site) {
        this.site = site;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
