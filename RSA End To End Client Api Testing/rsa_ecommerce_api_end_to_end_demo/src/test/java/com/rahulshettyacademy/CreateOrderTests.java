package com.rahulshettyacademy;

import com.rahulshettyacademy.pojo.AddProductPojo;
import com.rahulshettyacademy.pojo.CreateOrdersObj;
import com.rahulshettyacademy.pojo.OrderObj;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class CreateOrderTests  extends BaseTest {

    @Test
    public void test_createOrder() {
        JsonPath addProductJsp = addProduct(new AddProductPojo(authToken, userId));

        productId = addProductJsp.getString("productId");

        JsonPath getOrdersForUserJsp = getAllOrdersForCustomer(authToken, userId);

        int countOrdersBefore = getOrdersForUserJsp.getInt("data.size()");

        OrderObj order1 = new OrderObj(productId);
        CreateOrdersObj createOrdersObj = new CreateOrdersObj(new ArrayList<OrderObj>(Arrays.asList(order1)));

        JsonPath createOrderJsp = createOrder(authToken, createOrdersObj);

        String resultProductId = createOrderJsp.getString("productOrderId[0]");

        orderId = createOrderJsp.getString("orders[0]");

        Assert.assertEquals(resultProductId, productId);

        JsonPath getOrdersForUserAfterJsp = getAllOrdersForCustomer(authToken, userId);

        int countOrdersAfter = getOrdersForUserAfterJsp.getInt("data.size()");

        Assert.assertTrue(countOrdersAfter == countOrdersBefore + 1);

        deleteOrder(authToken, orderId);
    }
}
