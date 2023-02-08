package com.rahulshettyacademy.pojo.jiraapiobjects;

public class CreateProjectPojo {
    private String description = "Example project for testing rest api";
    private String name;
    private String projectTypeKey = "software";
    private String key;
    private String lead = "olegati1";

    public CreateProjectPojo(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProjectTypeKey() {
        return projectTypeKey;
    }

    public void setProjectTypeKey(String projectTypeKey) {
        this.projectTypeKey = projectTypeKey;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLead() {
        return lead;
    }

    public void setLead(String lead) {
        this.lead = lead;
    }
}
