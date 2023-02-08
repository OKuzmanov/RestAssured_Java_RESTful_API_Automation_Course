package com.rahulshettyacademy.pojo;

import com.github.javafaker.Faker;

import java.io.File;

public class AddProductPojo {
   private String authorization;
   private String productName;
   private String productAddedBy;
   private String productCategory;
   private String productSubCategory;
   private String productPrice;
   private String productDescription;
   private String productFor;
   private File productImage;

    public AddProductPojo(String authToken, String userId) {
        Faker faker = Faker.instance();
        this.authorization = authToken;
        this.productName = "Iphone11";
        this.productAddedBy = userId;
        this.productCategory = "electronics";
        this.productSubCategory = "mobiles";
        this.productPrice = faker.number().digit();
        this.productDescription = faker.lorem().characters();
        this.productFor = "men";
        this.productImage = new File(System.getProperty("user.dir") + "\\resources\\iphone11.jpg");
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductAddedBy() {
        return productAddedBy;
    }

    public void setProductAddedBy(String productAddedBy) {
        this.productAddedBy = productAddedBy;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductSubCategory() {
        return productSubCategory;
    }

    public void setProductSubCategory(String productSubCategory) {
        this.productSubCategory = productSubCategory;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductFor() {
        return productFor;
    }

    public void setProductFor(String productFor) {
        this.productFor = productFor;
    }

    public File getProductImage() {
        return productImage;
    }

    public void setProductImage(File productImage) {
        this.productImage = productImage;
    }
}
