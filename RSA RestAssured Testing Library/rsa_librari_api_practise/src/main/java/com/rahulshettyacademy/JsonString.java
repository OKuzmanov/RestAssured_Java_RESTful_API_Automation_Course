package com.rahulshettyacademy;

public class JsonString {

    public static String addBookPayload(String name, String isbn, String aisle, String author) {
        return "{\n" +
                "\n" +
                "\"name\":\"" + name + "\",\n" +
                "\"isbn\":\"" + isbn + "\",\n" +
                "\"aisle\":\"" + aisle + "\",\n" +
                "\"author\":\"" + author + "\"\n" +
                "}\n";
    }

    public static String deleteBookPayload(String id) {
        return "{\n" +
                "\"ID\" : \"" + id + "\"\n" +
                "} \n";
    }
}
