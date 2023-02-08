package com.rahulshettyacademy.pojo.getcoursesapiobjects;

import java.util.ArrayList;

public class Courses {
    private ArrayList<Course> webAutomation;
    private ArrayList<Course> api;
    private ArrayList<Course> mobile;

    public ArrayList<Course> getWebAutomation() {
        return webAutomation;
    }

    public void setWebAutomation(ArrayList<Course> webAutomation) {
        this.webAutomation = webAutomation;
    }

    public ArrayList<Course> getApi() {
        return api;
    }

    public void setApi(ArrayList<Course> api) {
        this.api = api;
    }

    public ArrayList<Course> getMobile() {
        return mobile;
    }

    public void setMobile(ArrayList<Course> mobile) {
        this.mobile = mobile;
    }
}
