package com.merakianalytics.kernel.riotapi.data;

import java.net.HttpURLConnection;
import java.util.Map;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;

import org.jboss.resteasy.annotations.GZIP;

import com.google.common.collect.ImmutableMap;
import com.merakianalytics.kernel.riotapi.RiotAPIService;
import com.merakianalytics.orianna.types.common.Platform;
import com.merakianalytics.orianna.types.data.champion.ChampionStatus;
import com.merakianalytics.orianna.types.data.champion.ChampionStatuses;

import io.swagger.annotations.Api;

/**
 * The Champion Status API proxy for the Riot API
 *
 * @see https://developer.riotgames.com/api-methods/#champion-v3
 */
@Path("/platform/v3")
@Api("Champion Status API")
@GZIP
public class ChampionAPI extends RiotAPIService {
    /**
     * /lol/platform/v3/champions
     *
     * @see https://developer.riotgames.com/api-methods/#champion-v3/GET_getChampions
     *
     * @param platform
     *        the tag for the {@link com.merakianalytics.orianna.types.common.Platform} to get data from. If null, the default
     *        {@link com.merakianalytics.orianna.types.common.Platform} will be used.
     * @param freeToPlay
     *        whether to only get free to play champions (default: false)
     * @return {@link com.merakianalytics.orianna.types.data.champion.ChampionStatuses}
     */
    @Path("/champions")
    @GET
    public ChampionStatuses getChampions(@QueryParam("platform") final String platformTag,
        @QueryParam("freeToPlay") @DefaultValue("false") final boolean freeToPlay) {
        final Platform platform = platformTag != null ? Platform.withTag(platformTag) : context.getDefaultPlatform();
        if(platform == null) {
            throw new WebApplicationException(platformTag + " is not a valid platform!", HttpURLConnection.HTTP_BAD_REQUEST);
        }

        final Map<String, Object> query = ImmutableMap.<String, Object> builder()
            .put("platform", platform)
            .put("freeToPlay", freeToPlay)
            .build();

        return context.getPipeline().get(ChampionStatuses.class, query);
    }

    /**
     * /lol/platform/v3/champions/{id}
     *
     * @see https://developer.riotgames.com/api-methods/#champion-v3/GET_getChampionsById
     *
     * @param platform
     *        the tag for the {@link com.merakianalytics.orianna.types.common.Platform} to get data from. If null, the default
     *        {@link com.merakianalytics.orianna.types.common.Platform} will be used.
     * @param id
     *        the champion's id
     * @return {@link com.merakianalytics.orianna.types.data.champion.ChampionStatus}
     */
    @Path("/champions/{id}")
    @GET
    public ChampionStatus getChampionsById(@QueryParam("platform") final String platformTag, @PathParam("id") final int id) {
        final Platform platform = platformTag != null ? Platform.withTag(platformTag) : context.getDefaultPlatform();
        if(platform == null) {
            throw new WebApplicationException(platformTag + " is not a valid platform!", HttpURLConnection.HTTP_BAD_REQUEST);
        }

        final Map<String, Object> query = ImmutableMap.<String, Object> builder()
            .put("platform", platform)
            .put("id", id)
            .build();

        return context.getPipeline().get(ChampionStatus.class, query);
    }
}
