package com.rahulshettyacademy.RestEntities;

import com.rahulshettyacademy.utils.GraphqlQueries;

public class EpisodeRest extends BaseEntity {

    public static String getEpisode(String id) {
        return getGenericRequest(GraphqlQueries.getEpisode(id));
    }

    public static String createEpisode(String name, String airDate, String episode) {
        return getGenericRequest(GraphqlQueries.createEpisode(name, airDate, episode));
    }

    public static String deleteEpisode(String... varArgs) {
        return getGenericRequest(GraphqlQueries.deleteEpisodes(varArgs));
    }

    public static String associateCharacterWithEpisode(String episodeId, String characterId) {
        return getGenericRequest(GraphqlQueries.associateCharacterWithEpisode(episodeId, characterId));
    }

    public static String getAllEpisodes() {
        return getGenericRequest(GraphqlQueries.getAllEpisodes());
    }

    public static String getAllEpisodes(String name, String episode) {
        return getGenericRequest(GraphqlQueries.getAllEpisodes(name, episode));
    }

    public static String getEpisodesByIds(String... varArgs) {
        return getGenericRequest(GraphqlQueries.getEpisodesByIds(varArgs));
    }
}
