package com.codingburg.covid19.model;

public class Data {
    String country;
    String cases;
    String deaths;
    String todayCases;
    String todayDeaths;
    String flag;

    public Data(String country, String cases, String deaths, String todayCases, String todayDeaths, String flag) {
        this.country = country;
        this.cases = cases;
        this.deaths = deaths;
        this.todayCases = todayCases;
        this.todayDeaths = todayDeaths;
        this.flag = flag;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCases() {
        return cases;
    }

    public void setCases(String cases) {
        this.cases = cases;
    }

    public String getDeaths() {
        return deaths;
    }

    public void setDeaths(String deaths) {
        this.deaths = deaths;
    }

    public String getTodayCases() {
        return todayCases;
    }

    public void setTodayCases(String todayCases) {
        this.todayCases = todayCases;
    }

    public String getTodayDeaths() {
        return todayDeaths;
    }

    public void setTodayDeaths(String todayDeaths) {
        this.todayDeaths = todayDeaths;
    }
}
