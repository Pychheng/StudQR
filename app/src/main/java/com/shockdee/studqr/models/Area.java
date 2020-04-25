package com.shockdee.studqr.models;

public class Area {

    public int area_ID;
    public String area_name;

    public Area(){

    }

    public Area(String area_name) {
        this.area_name = area_name;
    }

    public Area(int area_ID, String area_name) {
        this.area_ID = area_ID;
        this.area_name = area_name;
    }

    public int getArea_ID() {
        return area_ID;
    }

    public void setArea_ID(int area_ID) {
        this.area_ID = area_ID;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }
}
