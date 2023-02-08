package com.rahulshettyacademy;

import io.restassured.path.json.JsonPath;

public class App
{
    public static void main( String[] args )
    {
        //1. Print No of courses returned by API
        System.out.println("//Print No of courses returned by API");
        JsonPath js = new JsonPath(JsonText.getText());
        int coursesCount1 = js.getList("courses").size();

        int coursesCount2 = js.getInt("courses.size()");

        System.out.println(coursesCount1);
        System.out.println(coursesCount2);

        //2.Print Purchase Amount
        System.out.println("//Print Purchase Amount");
        int purchaseAmount = js.getInt("dashboard.purchaseAmount");
        System.out.println(purchaseAmount);

        System.out.println("//Print Website");
        String website = js.getString("dashboard.website");
        System.out.println(website);

        //3. Print Title of the first course
        System.out.println("//Print Title of the first course");
        String firstTitle = js.getString("courses[0].title");
        System.out.println(firstTitle);

        //4. Print All course titles and their respective Prices + copies
        System.out.println("//Print All course titles and their respective Prices + copies");
        int count = js.getInt("courses.size()");
        for (int i = 0; i < count; i++) {
            String title = js.getString("courses[" + i + "].title");
            int price = js.getInt("courses[" + i + "].price");
            int copies = js.getInt("courses[" + i + "].copies");
            System.out.println(title);
            System.out.println(price);
            System.out.println(copies);
        }

        //5. Print no of copies sold by RPA Course
        System.out.println("//Print no of copies sold by RPA Course");
        for (int i = 0; i < count; i++) {
            String title = js.getString("courses[" + i + "].title");
            if (title.equals("RPA")) {
                int rpaCopies = js.getInt("courses[" + i + "].copies");
                System.out.println(rpaCopies);
                break;
            }
        }

        //6. Verify if Sum of all Course prices matches with Purchase Amount
        System.out.println("//Verify if Sum of all Course prices matches with Purchase Amount");
        int sum = 0;
        for (int i = 0; i < count; i++) {
            int price = js.getInt("courses[" + i + "].price");
            int copies = js.getInt("courses[" + i + "].copies");
            sum += price * copies;
        }
        int amount = js.getInt("dashboard.purchaseAmount");
        System.out.println(sum + " = " + amount);
    }
}
