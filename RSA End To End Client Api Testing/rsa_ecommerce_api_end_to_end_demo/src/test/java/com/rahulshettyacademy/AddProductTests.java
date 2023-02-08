package com.rahulshettyacademy;

import com.rahulshettyacademy.pojo.AddProductPojo;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

public class AddProductTests extends BaseTest {

    @Test
    public void test_AddProduct() {
        JsonPath getAllProductsJsp = getAllProducts(authToken);

        int prodCountBefore = getAllProductsJsp.getInt("count");

        JsonPath addProductJsp = addProduct(new AddProductPojo(authToken, userId));

        productId = addProductJsp.getString("productId");

        System.out.println(productId);

        JsonPath getAllProductsAfterJsp = getAllProducts(authToken);

        int prodCountAfter = getAllProductsAfterJsp.getInt("count");

        Assert.assertTrue(prodCountAfter == prodCountBefore + 1);

        deleteProduct(authToken, productId);
    }
}
