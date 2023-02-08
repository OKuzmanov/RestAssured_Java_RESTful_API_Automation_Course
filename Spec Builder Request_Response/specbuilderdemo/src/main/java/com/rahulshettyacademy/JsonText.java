package com.rahulshettyacademy;

public class JsonText {

    public static String createProjectPayload(String projectName, String projectKey) {
        return "{\n" +
                "  \"description\": \"Example project for testing rest api\",\n" +
                "  \"name\": \"" + projectName + "\",\n" +
                "  \"projectTypeKey\": \"software\",\n" +
                "  \"key\": \"" + projectKey + "\",\n" +
                "  \"lead\": \"olegati1\"\n" +
                "}";
    }

    public static String loginPayload(String username, String password) {
        return "{ \n" +
                "    \"username\": \"" + username + "\", \n" +
                "    \"password\": \"" + password + "\" \n" +
                "}";
    }

    public static String addIssuePayLoad(String projectKey, String summary, String description, String issueType) {
        return "{\n" +
                "    \"fields\": {\n" +
                "        \"project\":\n" +
                "        {\n" +
                "            \"key\": \"" + projectKey + "\"" +
                "        },\n" +
                "        \"summary\": \"" + summary + "\",\n" +
                "        \"description\": \"" + description + "\",\n" +
                "        \"issuetype\": {\n" +
                "            \"name\": \"" + issueType + "\"\n" +
                "        }\n" +
                "    }\n" +
                "}";
    }

    public static String editIssuePayLoad(String summary, String description) {
        return "{\n" +
                "    \"fields\": {\n" +
                "        \"summary\": \"" + summary + "\",\n" +
                "        \"description\": \"" + description + "\"\n" +
                "    }\n" +
                "}";
    }

    public static String addCommentPayLoad(String comment) {
        return "{\"body\": \"" + comment + "\"}";
    }

    public static String editCommentPayLoad(String comment) {
        return "{\"body\": \"" + comment + "\"}";
    }
}
