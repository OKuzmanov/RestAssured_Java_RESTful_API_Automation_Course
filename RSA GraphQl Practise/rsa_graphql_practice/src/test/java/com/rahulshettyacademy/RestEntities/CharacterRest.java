package com.rahulshettyacademy.RestEntities;

import com.rahulshettyacademy.utils.GraphqlQueries;

public class CharacterRest extends BaseEntity {

    public static String getCharacter(String charId) {
    return getGenericRequest(GraphqlQueries.getCharacter(charId));
    }

    public static String createCharacter(String name, String type, String status, String species, String gender, String image, String originId, String locationId) {
        return getGenericRequest(GraphqlQueries.createCharacter(name, type, status, species, gender, image, originId, locationId));
    }

    public static String deleteCharacter(String... varArgs) {
        return getGenericRequest(GraphqlQueries.deleteCharacters(varArgs));
    }

    public static String getAllCharacters() {
        return getGenericRequest(GraphqlQueries.getAllCharacters());
    }

    public static String getAllCharacters(String name, String status, String species, String type, String gender) {
        return getGenericRequest(GraphqlQueries.getAllCharacters(name, status, species, type, gender));
    }

    public static String getCharactersByIds(String... varArgs) {
        return getGenericRequest(GraphqlQueries.getCharactersByIds(varArgs));
    }
}
