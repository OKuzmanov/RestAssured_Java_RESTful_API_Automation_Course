package com.rahulshettyacademy.tests;

import com.rahulshettyacademy.RestEntities.LocationRest;
import com.rahulshettyacademy.pojos.LocationPojo;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Random;

public class LocationTests extends BaseTest {
    @Test
    public void test_locationCreatedSuccessfully() {
        String locName = "Beverly Hills";
        String locType = "Neighbourhood";
        String locDimension = "0432";

        String createdLocationResponse = LocationRest.createLocation(locName, locType, locDimension);

        String newLocationId = new JsonPath(createdLocationResponse).getString("data.createLocation.id");

        String getLocationResponse = LocationRest.getLocation(newLocationId);

        JsonPath jsp = new JsonPath(getLocationResponse);

        Assert.assertEquals(locName, jsp.getString("data.location.name"));
        Assert.assertEquals(locType, jsp.getString("data.location.type"));
        Assert.assertEquals(locDimension, jsp.getString("data.location.dimension"));
    }

    @Test
    public void test_deleteLocation() {
        String locName = "Beverly Hills";
        String locType = "Neighbourhood";
        String locDimension = "0432";

        String createdLocationResponse = LocationRest.createLocation(locName, locType, locDimension);

        String newLocationId = new JsonPath(createdLocationResponse).getString("data.createLocation.id");

        LocationRest.getLocation(newLocationId);

        String getAllLocsResp = LocationRest.getAllLocations();

        String countLocationsBefore = new JsonPath(getAllLocsResp).getString("data.locations.info.count");

        String deleteLocationResp = LocationRest.deleteLocations(newLocationId);

        String countDeleted = new JsonPath(deleteLocationResp).getString("data.deleteLocations.locationsDeleted");

        Assert.assertEquals(1, Integer.valueOf(countDeleted));

        String getAllLocsRespAfter = LocationRest.getAllLocations();

        String countLocationsAfter = new JsonPath(getAllLocsRespAfter).getString("data.locations.info.count");

        Assert.assertTrue(Integer.parseInt(countLocationsBefore) == Integer.parseInt(countLocationsAfter) + 1);
    }

    @Test
    public void test_editLocation() {
        String locName = "Beverly Hills";
        String locType = "Neighbourhood";
        String locDimension = "0432";

        String createdLocationResponse = LocationRest.createLocation(locName, locType, locDimension);

        String locationId = new JsonPath(createdLocationResponse).getString("data.createLocation.id");

        int rnd = new Random().nextInt();
        String newLocName = "Beverly Hills" + rnd;
        String newLocType = "Neighbourhood" + rnd;
        String newLocDimension = "0432" + rnd;

        String editLocResp = LocationRest.editLocation(locationId, newLocName, newLocType, newLocDimension);

        String status = new JsonPath(editLocResp).getString("data.editLocation.status");

        Assert.assertEquals(status, "1");

        String getLocationResponse = LocationRest.getLocation(locationId);

        JsonPath jsp = new JsonPath(getLocationResponse);

        Assert.assertEquals(newLocName, jsp.getString("data.location.name"));
        Assert.assertEquals(newLocType, jsp.getString("data.location.type"));
        Assert.assertEquals(newLocDimension, jsp.getString("data.location.dimension"));
    }

    @Test
    public void test_getAllLocationsByFilter() {

        int rnd = new Random().nextInt();

        String name = "rndName" + rnd;
        String type = "locType" + rnd;
        String dimension = "rndDimension" + rnd;

        int expectedCount = 4;

        for (int i = 0; i < expectedCount; i++) {
            LocationRest.createLocation(name, type, dimension);
        }

        String getAllLocsResp = LocationRest.getAllLocations(name, type, dimension);

        JsonPath jsp = new JsonPath(getAllLocsResp);

        Assert.assertEquals(jsp.getInt("data.locations.info.count") , expectedCount);

        for (int i = 0; i < expectedCount; i++) {
            Assert.assertEquals(jsp.getString("data.locations.result[" + i + "].name"), name);
            Assert.assertEquals(jsp.getString("data.locations.result[" + i + "].type"), type);
            Assert.assertEquals(jsp.getString("data.locations.result[" + i + "].dimension"), dimension);
        }
    }

    @Test
    public void test_testGetLocationsByIds() {

        int locLimit = 5;

        String allLocationsResp = LocationRest.getAllLocations();

        List<LocationPojo> allLocations = new JsonPath(allLocationsResp).getList("data.locations.result", LocationPojo.class);

        String getLocByIdsResp = LocationRest.getLocationsByIds(allLocations.stream().limit(locLimit).map(LocationPojo::getId).toArray(String[]::new));

        List<LocationPojo> fetchedLocations = new JsonPath(getLocByIdsResp).getList("data.locationsByIds", LocationPojo.class);

        for (int i = 0; i < fetchedLocations.size(); i++) {
            Assert.assertEquals(fetchedLocations.get(i).toString(), allLocations.get(i).toString());
        }
    }
}
