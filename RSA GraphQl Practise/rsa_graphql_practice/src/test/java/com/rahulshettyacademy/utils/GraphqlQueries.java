package com.rahulshettyacademy.utils;

import java.util.Arrays;

public class GraphqlQueries {

    public static String getLocation(String id) {
        return "{\n" +
                "   \"query\":\"query {\\n  location(locationId: " + id + "){\\n    name\\n    type\\n    id\\n    dimension\\n    residents{\\n      name\\n    }\\n    created\\n  }\\n}\\n\\n\",\n" +
                "   \"variables\":null\n" +
                "}";
    }

    public static String createLocation(String locationName, String locationType, String dimension) {
        return "{\"query\":\"mutation($locationName: String!, $type: String!, $dimension: String!) {\\n  createLocation(location: {name: $locationName, type: $type, dimension: $dimension}) {\\n    id\\n  }\\n}\\n\"," +
                "\"variables\":{\"locationName\":\"" + locationName + "\",\"type\":\"" + locationType + "\",\"dimension\":\"" + dimension + "\"}}";
    }

    public static String deleteLocations(String... varArgs) {
        return "{\"query\":\"mutation($locationIds: [Int!]!) {\\n  deleteLocations(locationIds: $locationIds){\\n    locationsDeleted\\n  }\\n}\"," +
                "\"variables\":{\"locationIds\":" + Arrays.toString(varArgs) + "}}";
    }

    public static String editLocation(String locationId, String newName, String newType, String newDimension) {
        return "{\"query\":\"mutation($locationId: Int!, $newName: String, $newType: String, $newDimension: String) {\\n  editLocation(locationId: $locationId, newLocationData: {name: $newName, type: $newType, dimension: $newDimension}){\\n    status\\n  }\\n}\"," +
                "\"variables\":{\"locationId\":" + locationId + ",\"newName\":\"" + newName + "\",\"newType\":\"" + newType + "\",\"newDimension\":\"" + newDimension + "\"}}";
    }

    public static String getCharacter(String charId) {
        return "{\"query\":\"query {\\n  character(characterId: " + charId + ") {\\n    name\\n    type\\n    status\\n    species\\n    gender\\n    image\\n    location{\\n      id\\n      name\\n    }\\n    origin{\\n      id\\n      name\\n    }\\n    episodes{\\n      id,\\n      name,\\n      air_date\\n      episode\\n      characters{\\n        id,\\n        name\\n      }\\n    }\\n  }\\n}\\n\"," +
                "\"variables\":{}}";
    }

    public static String createCharacter(String name, String type, String status, String species, String gender, String image, String originId, String locationId) {
        return "{\"query\":\"mutation ($name: String!, $type: String!, $status: String!, $species: String!, $gender: String!, $image: String!, $originId: Int!, $locationId: Int!) {\\n  createCharacter(character: {name: $name, type: $type, status: $status, species: $species, gender: $gender, image: $image, originId: $originId, locationId: $locationId}) {\\n    id\\n  }\\n}\\n\"," +
                "\"variables\":{\"name\":\"" + name + "\",\"type\":\"" + type + "\",\"status\":\"" + status + "\",\"species\":\"" + species + "\",\"gender\":\"" + gender + "\",\"image\":\"" + image + "\",\"originId\":" + originId + ",\"locationId\":" + locationId + "}}";
    }

    public static String deleteCharacters(String... varArgs) {
        return "{\"query\":\"mutation($characterIds: [Int!]) {\\n  deleteCharacters(characterIds: $characterIds) {\\n    charactersDeleted\\n  }\\n}\"," +
                "\"variables\":{\"characterIds\":" + Arrays.toString(varArgs) + "}}";
    }

    public static String createEpisode(String name, String airDate, String episode) {
        return "{\"query\":\"mutation($name: String!, $air_date: String!, $episode: String!) {\\n  createEpisode(episode: {name: $name, air_date: $air_date, episode: $episode}) {\\n    id\\n  }\\n}\"," +
                "\"variables\":{\"name\":\"" + name + "\",\"air_date\":\"" + airDate + "\",\"episode\":\"" + episode + "\"}}";
    }

    public static String getEpisode(String id) {
        return "{\"query\":\"\\nquery {\\n episode(episodeId: " + id + "){\\n  name\\n  air_date\\n  episode\\n  characters{\\n    name\\n    type\\n    status\\n    species\\n    gender\\n  }\\n} \\n}\"," +
                "\"variables\":null}";
    }

    public static String deleteEpisodes(String... varArgs) {
        return "{\"query\":\"mutation($episodeIds: [Int!]) {\\n  deleteEpisodes(episodeIds: $episodeIds) {\\n    episodesDeleted\\n  }\\n}\"," +
                "\"variables\":{\"episodeIds\":" + Arrays.toString(varArgs) + "}}";
    }

    public static String associateCharacterWithEpisode(String episodeId, String characterId) {
        return "{\"query\":\"\\nmutation($episodeId: Int!, $characterId: Int!) {\\n  associateEpisodeCharacter(episodeId: $episodeId, characterId: $characterId) {\\n    status\\n  }\\n}\"," +
                "\"variables\":{\"episodeId\":" + episodeId + ",\"characterId\":" + characterId + "}}";
    }

    public static String getAllLocations(String name, String type, String dimension) {
        return "{\"query\":\"query($name: String, $type: String, $dimension: String) {\\n  locations(filters: {name: $name, type: $type, dimension: $dimension}) {\\n    info{\\n      count\\n    }\\n    result{\\n      id\\n      name\\n      type\\n   dimension\\n   residents{\\n        id\\n        name\\n        type\\n        species\\n        gender\\n        status\\n      }\\n    }\\n  }\\n}\\n\\n\"," +
                "\"variables\":{\"name\":\"" + name + "\",\"type\":\"" + type + "\",\"dimension\":\"" + dimension + "\"}}";
    }

    public static String getAllLocations() {
        return "{\"query\":\"query($name: String, $type: String, $dimension: String) {\\n  locations(filters: {name: $name, type: $type, dimension: $dimension}) {\\n    info{\\n      count\\n    }\\n    result{\\n      id\\n      name\\n      type\\n   dimension\\n   residents{\\n        id\\n        name\\n        type\\n        species\\n        gender\\n        status\\n      }\\n    }\\n  }\\n}\\n\\n\"," +
                "\"variables\":null}";
    }

    public static String getLocationsByIds(String... varArgs) {
        return "{\"query\":\"query($locationIds: [Int!]!) {\\n  locationsByIds(ids: $locationIds){\\n  id\\n  name\\n    type\\n    dimension\\n    residents{\\n        id\\n        name\\n        type\\n        species\\n        gender\\n        status\\n      }\\n  }\\n}\\n\\n\"," +
                "\"variables\":{\"locationIds\":" + Arrays.toString(varArgs) + "}}";
    }

    public static String getAllCharacters() {
        return "{\"query\":\"query {\\n  characters(filters: {}){\\n    info{\\n      count\\n    }\\n    result{\\n      id\\n      name\\n      type\\n      status\\n      species\\n      gender\\n   image\\n   \\n    }\\n  }\\n}\\n\\n\"," +
                "\"variables\":null}";
    }

    public static String getAllCharacters(String name, String status, String species, String type, String gender) {
        return "{\"query\":\"query($name: String, $status: String, $species: String, $type: String, $gender: String) {\\n  characters(filters: {name: $name, status: $status, species: $species, type: $type, gender: $gender}){\\n    info{\\n      count\\n    }\\n    result{\\n      id\\n      name\\n      type\\n      status\\n      species\\n      gender\\n   image\\n   \\n    }\\n  }\\n}\\n\\n\"," +
                "\"variables\":{\"name\":\"" + name + "\",\"status\":\"" + status + "\",\"species\":\"" + species + "\",\"type\":\"" + type + "\",\"gender\":\"" + gender + "\"}}";
    }

    public static String getCharactersByIds(String... varArgs) {
        return "{\"query\":\"query($characterIds: [Int!]!) {\\n charactersByIds(ids: $characterIds){\\n  name\\n  type\\n  status\\n  species\\n  gender\\n image\\n}\\n}\\n\\n\"," +
                "\"variables\":{\"characterIds\":" + Arrays.toString(varArgs) + "}}";
    }

    public static String getAllEpisodes() {
        return "{\"query\":\"query {\\n  episodes(filters: {}){\\n    info{\\n      count\\n    }\\n    result{\\n      id\\n      name\\n      air_date\\n      episode\\n      characters{\\n        id\\n        name\\n        type\\n      }\\n    }\\n  }\\n}\"," +
                "\"variables\":null}";
    }

    public static String getAllEpisodes(String name, String episode) {
        return "{\"query\":\"query($name: String, $episode: String) {\\n  episodes(filters: {name: $name, episode: $episode}){\\n    info{\\n      count\\n    }\\n    result{\\n      id\\n      name\\n      air_date\\n      episode\\n      characters{\\n        id\\n        name\\n        type\\n      }\\n    }\\n  }\\n}\"," +
                "\"variables\":{\"name\":\"" + name + "\",\"episode\":\"" + episode + "\"}}";
    }

    public static String getEpisodesByIds(String... varArgs) {
        return "{\"query\":\"query($ids: [Int!]!) {\\n  episodesByIds(ids: $ids){\\n    name\\n    air_date\\n    episode\\n    characters{\\n      name\\n      type\\n      status\\n      species\\n      gender\\n      image\\n    }\\n  }\\n}\"," +
                "\"variables\":{\"ids\":" + Arrays.toString(varArgs) + "}}";
    }
}
