package com.rahulshettyacademy.RestEntities;

import com.rahulshettyacademy.utils.GraphqlQueries;

import static io.restassured.RestAssured.given;

public class LocationRest extends BaseEntity {

    public static String createLocation(String locationName, String locationType, String dimension) {
        return getGenericRequest(GraphqlQueries.createLocation(locationName, locationType, dimension));
    }

    public static String getLocation(String id) {
        return getGenericRequest(GraphqlQueries.getLocation(id));
    }

    public static String deleteLocations(String... varArgs) {
        return getGenericRequest(GraphqlQueries.deleteLocations(varArgs));
    }

    public static String editLocation(String locationId, String newName, String newType, String newDimension) {
        return getGenericRequest(GraphqlQueries.editLocation(locationId, newName, newType, newDimension));
    }

    public static String getAllLocations(String name, String type, String dimension) {
        return getGenericRequest(GraphqlQueries.getAllLocations(name, type, dimension));
    }

    public static String getAllLocations() {
        return getGenericRequest(GraphqlQueries.getAllLocations());
    }

    public static String getLocationsByIds(String... varArgs) {
        return getGenericRequest(GraphqlQueries.getLocationsByIds(varArgs));
    }
}
