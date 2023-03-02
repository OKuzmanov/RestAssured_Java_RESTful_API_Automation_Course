package com.rahulshettyacademy.tests;

import com.rahulshettyacademy.RestEntities.CharacterRest;
import com.rahulshettyacademy.RestEntities.LocationRest;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

public class CharacterTests extends BaseTest {

    @Test
    public void test_createCharacter() {
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

        String getCharResp = CharacterRest.getCharacter(charId);

        JsonPath jspGetChar = new JsonPath(getCharResp);

        Assert.assertEquals(jspGetChar.getString("data.character.name"), charName);
        Assert.assertEquals(jspGetChar.getString("data.character.type"), charType);
        Assert.assertEquals(jspGetChar.getString("data.character.status"), charStatus);
        Assert.assertEquals(jspGetChar.getString("data.character.species"), charSpecies);
        Assert.assertEquals(jspGetChar.getString("data.character.gender"), charGender);
        Assert.assertEquals(jspGetChar.getString("data.character.image"), charImage);
    }

    @Test
    public void test_deleteCharacters() {
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

        String getAllCharsResp = CharacterRest.getAllCharacters();

        String countCharsBefore = new JsonPath(getAllCharsResp).getString("data.characters.info.count");

        String deleteCharResp = CharacterRest.deleteCharacter(charId);

        String deletedCharCount = new JsonPath(deleteCharResp).getString("data.deleteCharacters.charactersDeleted");

        Assert.assertEquals(deletedCharCount, "1");

        String getCharResp = CharacterRest.getCharacter(charId);

        String errMsgActual = new JsonPath(getCharResp).getString("errors[0].message");

        Assert.assertEquals(errMsgActual, errMsgExpected);

        String getAllCharsAfterResp = CharacterRest.getAllCharacters();

        String countCharsAfter = new JsonPath(getAllCharsAfterResp).getString("data.characters.info.count");

        Assert.assertTrue(Integer.parseInt(countCharsAfter) == Integer.parseInt(countCharsBefore) - 1);
    }

    @Test
    public void test_getAllCharactersByFilter() {

        int count = 4;

        String locName = "Beverly Hills";
        String locType = "Neighbourhood";
        String locDimension = "0432";

        String createdLocationResponse = LocationRest.createLocation(locName, locType, locDimension);

        String locationId = new JsonPath(createdLocationResponse).getString("data.createLocation.id");

        int rnd = new Random().nextInt();
        String charName = "Robbin" + rnd;
        String charType = "Side kick" + rnd;
        String charStatus = "Alive" + rnd;
        String charSpecies = "Human" + rnd;
        String charGender = "Male" + rnd;
        String charImage = "noImage.jpeg" + rnd;

        for (int i = 0; i < count; i++) {
            CharacterRest.createCharacter(charName, charType, charStatus, charSpecies, charGender, charImage, locationId, locationId);
        }

        String getAllCharsByFilter = CharacterRest.getAllCharacters(charName, charStatus, charSpecies, charType, charGender);

        JsonPath jsp = new JsonPath(getAllCharsByFilter);

        for (int i = 0; i < count; i++) {
            Assert.assertEquals(jsp.getString("data.characters.result[" + i + "].name"), charName);
            Assert.assertEquals(jsp.getString("data.characters.result[" + i + "].type"), charType);
            Assert.assertEquals(jsp.getString("data.characters.result[" + i + "].status"), charStatus);
            Assert.assertEquals(jsp.getString("data.characters.result[" + i + "].species"), charSpecies);
            Assert.assertEquals(jsp.getString("data.characters.result[" + i + "].gender"), charGender);
            Assert.assertEquals(jsp.getString("data.characters.result[" + i + "].image"), charImage);
        }
    }

    @Test
    public void test_getLocationByIds() {
        int limit = 5;

        String getAllCharsResp = CharacterRest.getAllCharacters();

        List<LinkedHashMap<String, String>> allChars = new JsonPath(getAllCharsResp).getList("data.characters.result");

        String getCharsByIdsResp = CharacterRest.getCharactersByIds(allChars.stream()
                .limit(limit)
                .map(e -> String.valueOf(e.get("id")))
                .toArray(String[]::new));

        List<LinkedHashMap<String, String>> fetchedChars = new JsonPath(getCharsByIdsResp).getList("data.charactersByIds");

        for (int i = 0; i < fetchedChars.size(); i++) {
            Assert.assertEquals(fetchedChars.get(i).get("name"), allChars.get(i).get("name"));
            Assert.assertEquals(fetchedChars.get(i).get("type"), allChars.get(i).get("type"));
            Assert.assertEquals(fetchedChars.get(i).get("status"), allChars.get(i).get("status"));
            Assert.assertEquals(fetchedChars.get(i).get("species"), allChars.get(i).get("species"));
            Assert.assertEquals(fetchedChars.get(i).get("gender"), allChars.get(i).get("gender"));
            Assert.assertEquals(fetchedChars.get(i).get("image"), allChars.get(i).get("image"));
        }
    }
}
