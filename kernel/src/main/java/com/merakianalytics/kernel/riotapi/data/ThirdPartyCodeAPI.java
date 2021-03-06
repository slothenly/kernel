package com.merakianalytics.kernel.riotapi.data;

import java.net.HttpURLConnection;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;

import org.jboss.resteasy.annotations.GZIP;

import com.google.common.collect.ImmutableMap;
import com.merakianalytics.kernel.riotapi.RiotAPIService;
import com.merakianalytics.orianna.types.common.Platform;
import com.merakianalytics.orianna.types.data.thirdpartycode.VerificationString;

import io.swagger.annotations.Api;

/**
 * The Third Party Code API proxy for the Riot API
 *
 * @see https://developer.riotgames.com/api-methods/#third-party-code-v3
 */
@Path("/platform/v3")
@Api("Third Party Code API")
@GZIP
public class ThirdPartyCodeAPI extends RiotAPIService {
    /**
     * /lol/platform/v3/third-party-code/by-summoner/{summonerId}
     *
     * @see https://developer.riotgames.com/api-methods/#third-party-code-v3/GET_getThirdPartyCodeBySummonerId
     *
     * @param platform
     *        platform the tag for the {@link com.merakianalytics.orianna.types.common.Platform} to get data from. If null, the default
     *        {@link com.merakianalytics.orianna.types.common.Platform} will be used.
     * @param summonerId
     *        the summoner's id
     * @return {@link com.merakianalytics.orianna.types.data.thirdpartycode.VerificationString}
     */
    @Path("/third-party-code/by-summoner/{summonerId}")
    @GET
    public VerificationString verificationString(@QueryParam("platform") final String platformTag, @PathParam("summonerId") final long summonerId) {
        final Platform platform = platformTag != null ? Platform.withTag(platformTag) : context.getDefaultPlatform();
        if(platform == null) {
            throw new WebApplicationException(platformTag + " is not a valid platform!", HttpURLConnection.HTTP_BAD_REQUEST);
        }

        final ImmutableMap.Builder<String, Object> builder = ImmutableMap.builder();
        builder.put("platform", platform);
        builder.put("summonerId", summonerId);

        return context.getPipeline().get(VerificationString.class, builder.build());
    }
}
