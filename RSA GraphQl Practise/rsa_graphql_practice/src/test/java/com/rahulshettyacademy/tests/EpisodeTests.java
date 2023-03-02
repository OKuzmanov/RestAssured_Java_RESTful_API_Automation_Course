package com.rahulshettyacademy.tests;

import com.rahulshettyacademy.RestEntities.CharacterRest;
import com.rahulshettyacademy.RestEntities.EpisodeRest;
import com.rahulshettyacademy.RestEntities.LocationRest;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

public class EpisodeTests extends BaseTest {

    @Test
    public void test_createEpisode() {

        String epName = "Batman and Robbin";
        String epAirDate = "10.10.1989";
        String broadcaster = "Netflix";

        String createEpResp = EpisodeRest.createEpisode(epName, epAirDate, broadcaster);

        String epId = new JsonPath(createEpResp).getString("data.createEpisode.id");

        String getEpResp = EpisodeRest.getEpisode(epId);

        JsonPath getEpJsp = new JsonPath(getEpResp);

        Assert.assertEquals(getEpJsp.getString("data.episode.name"), epName);
        Assert.assertEquals(getEpJsp.getString("data.episode.air_date"), epAirDate);
        Assert.assertEquals(getEpJsp.getString("data.episode.episode"), broadcaster);
    }

    @Test
    public void test_deleteEpisode() {
        String epName = "Batman and Robbin";
        String epAirDate = "10.10.1989";
        String broadcaster = "Netflix";

        String createEpResp = EpisodeRest.createEpisode(epName, epAirDate, broadcaster);

        String epId = new JsonPath(createEpResp).getString("data.createEpisode.id");

        String allEpisodesResp = EpisodeRest.getAllEpisodes();

        int allEpsCountBefore = new JsonPath(allEpisodesResp).getInt("data.episodes.info.count");

        String delEpResp = EpisodeRest.deleteEpisode(epId);

        String delEpsCount = new JsonPath(delEpResp).getString("data.deleteEpisodes.episodesDeleted");

        Assert.assertEquals(delEpsCount, "1");

        String getEpResp = EpisodeRest.getEpisode(epId);

        String errMsgActual = new JsonPath(getEpResp).getString("errors[0].message");

        Assert.assertEquals(errMsgActual, errMsgExpected);

        String allEpisodesAfterResp = EpisodeRest.getAllEpisodes();

        int allEpsCountAfter = new JsonPath(allEpisodesAfterResp).getInt("data.episodes.info.count");

        Assert.assertTrue(allEpsCountAfter == allEpsCountBefore - 1);
    }

    @Test
    public void test_associateCharacterWithEpisode() {
        String epName = "Batman and Robbin";
        String epAirDate = "10.10.1989";
        String broadcaster = "Netflix";

        String createEpResp = EpisodeRest.createEpisode(epName, epAirDate, broadcaster);

        String epId = new JsonPath(createEpResp).getString("data.createEpisode.id");

        String locName = "Beverly Hills";
        String locType = "Neighbourhood";
        String locDimension = "0432";

        String createdLocationResponse = LocationRest.createLocation(locName, locType, locDimension);

        String locationId = new JsonPath(createdLocationResponse).getString("data.createLocation.id");

        String charName = "Robbin";
        String charType = "Side kick";
        String charStatus = "Alive";
        String charSpecies = "Human";
        String charGender = "Male";
        String charImage = "noImage.jpeg";

        String createCharResp = CharacterRest.createCharacter(charName, charType, charStatus, charSpecies, charGender, charImage, locationId, locationId);

        String charId = new JsonPath(createCharResp).getString("data.createCharacter.id");

        String associateResp = EpisodeRest.associateCharacterWithEpisode(epId, charId);

        String status = new JsonPath(associateResp).getString("data.associateEpisodeCharacter.status");

        Assert.assertEquals(status, "1");

        String getEpResp = EpisodeRest.getEpisode(epId);

        JsonPath getEpJsp = new JsonPath(getEpResp);

        Assert.assertEquals(getEpJsp.getString("data.episode.characters[0].name"), charName);
        Assert.assertEquals(getEpJsp.getString("data.episode.characters[0].type"), charType);
        Assert.assertEquals(getEpJsp.getString("data.episode.characters[0].status"), charStatus);
        Assert.assertEquals(getEpJsp.getString("data.episode.characters[0].species"), charSpecies);
        Assert.assertEquals(getEpJsp.getString("data.episode.characters[0].gender"), charGender);
    }

    @Test
    public void test_getAllEpisodesByFilter() {
        int count = 4;

        int rnd = new Random().nextInt();
        String epName = "Batman and Robbin" + rnd;
        String epAirDate = "10.10.1989" + rnd;
        String broadcaster = "Netflix" + rnd;

        for (int i = 0; i < count; i++) {
            EpisodeRest.createEpisode(epName, epAirDate, broadcaster);
        }

        String allEpisodesResp = EpisodeRest.getAllEpisodes(epName, broadcaster);

        JsonPath jsp = new JsonPath(allEpisodesResp);

        Assert.assertEquals(jsp.getInt("data.episodes.info.count"), count);

        for (int i = 0; i < count; i++) {
            Assert.assertEquals(epName, jsp.getString("data.episodes.result["+i+"].name"));
            Assert.assertEquals(epAirDate, jsp.getString("data.episodes.result["+i+"].air_date"));
            Assert.assertEquals(broadcaster, jsp.getString("data.episodes.result["+i+"].episode"));
        }
    }

    @Test
    public void test_getAllEpisodesByIds() {
        int limit = 5;

        String allEpisodesRes = EpisodeRest.getAllEpisodes();

        List<LinkedHashMap<String, String>> allEpisodes = new JsonPath(allEpisodesRes).getList("data.episodes.result");

        String getAllEpsByIds = EpisodeRest.getEpisodesByIds(allEpisodes.stream()
                .limit(limit)
                .map(e -> String.valueOf(e.get("id")))
                .toArray(String[]::new));

        List<LinkedHashMap<String, String>> fetchedEpisodes = new JsonPath(getAllEpsByIds).getList("data.episodesByIds");

        for (int i = 0; i < fetchedEpisodes.size(); i++) {
            Assert.assertEquals(fetchedEpisodes.get(i).get("name"), allEpisodes.get(i).get("name"));
            Assert.assertEquals(fetchedEpisodes.get(i).get("air_date"), allEpisodes.get(i).get("air_date"));
            Assert.assertEquals(fetchedEpisodes.get(i).get("episode"), allEpisodes.get(i).get("episode"));
        }
    }
}
