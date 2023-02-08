package com.rahulshettyacademy.pojo;

import com.github.javafaker.Faker;

public class OrderObj {
    private String country;
    private String productOrderedId;

    public OrderObj(String productOrderedId) {
        this.country = Faker.instance().country().name();
        this.productOrderedId = productOrderedId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProductOrderedId() {
        return productOrderedId;
    }

    public void setProductOrderedId(String productOrderedId) {
        this.productOrderedId = productOrderedId;
    }
}
