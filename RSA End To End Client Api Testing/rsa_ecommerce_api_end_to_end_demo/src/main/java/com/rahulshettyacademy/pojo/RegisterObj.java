package com.rahulshettyacademy.pojo;

import com.github.javafaker.Faker;

public class RegisterObj {
    private String confirmPassword;
    private String firstName;
    private String gender;
    private String lastName;
    private String occupation;
    private boolean required;
    private String userEmail;
    private String userMobile;
    private String userPassword;
    private String userRole;

    public RegisterObj() {
        Faker faker = Faker.instance();
        String pass = "Oleg1997!";
        this.confirmPassword = pass;
        this.firstName = faker.name().firstName();
        this.gender = faker.demographic().sex();
        this.lastName = faker.name().lastName();
        this.occupation = occupation;
        this.required = true;
        this.userEmail = faker.internet().emailAddress();
        this.userMobile = faker.phoneNumber().subscriberNumber(10);
        this.userPassword = pass;
        this.userRole = "customer";
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}
