package com.rahulshettyacademy;

import com.rahulshettyacademy.pojo.AddProductPojo;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

public class DeleteProductTests extends BaseTest {

    @Test
    public void test_deleteProduct() {

        JsonPath addProductJsp = addProduct(new AddProductPojo(authToken, userId));

        productId = addProductJsp.getString("productId");

        JsonPath allProductsJsp = getAllProducts(authToken);
        int prodCountBefore = allProductsJsp.getInt("count");

        deleteProduct(authToken, productId);

        JsonPath allProductsAfterJsp = getAllProducts(authToken);
        int prodCountAfter = allProductsAfterJsp.getInt("count");

        Assert.assertTrue(prodCountAfter == prodCountBefore - 1);
    }
}
