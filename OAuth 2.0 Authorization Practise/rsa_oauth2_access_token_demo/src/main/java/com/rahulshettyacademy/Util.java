package com.rahulshettyacademy;

public class Util {

    public static String extractAccessToken(String url) {
        int beginIndex = url.indexOf("?code=") + 6;
        int endIndex = url.indexOf("&scope=");

        return url.substring(beginIndex, endIndex);
    }
}
