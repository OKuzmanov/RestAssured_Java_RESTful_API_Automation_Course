package com.rahulshettyacademy.pojo;

import java.util.ArrayList;

public class CreateOrdersObj {
    ArrayList<OrderObj> orders;

    public CreateOrdersObj(ArrayList<OrderObj> orders) {
        this.orders = orders;
    }

    public ArrayList<OrderObj> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<OrderObj> orders) {
        this.orders = orders;
    }
}
