package com.merakianalytics.kernel;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.merakianalytics.kernel.exceptions.OriannaExceptionMapper;
import com.merakianalytics.kernel.exceptions.QueryValidationExceptionMapper;
import com.merakianalytics.kernel.filters.CORSFilter;
import com.merakianalytics.kernel.providers.JSONProvider;
import com.merakianalytics.kernel.providers.MessagePackProvider;

import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;

/**
 * The jax-rs application definition. This is the "main" of the kernel application.
 */
@ApplicationPath("/")
public class WebServices extends Application {
    @Inject
    private KernelContext context;

    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> services = new HashSet<>();

        // Utilities
        services.add(OriannaExceptionMapper.class);
        services.add(QueryValidationExceptionMapper.class);
        services.add(CORSFilter.class);
        services.add(JSONProvider.class);
        services.add(MessagePackProvider.class);

        // Riot API
        if(context.isProduceCoreData()) {
            services.add(com.merakianalytics.kernel.riotapi.data.ChampionMasteryAPI.class);
            services.add(com.merakianalytics.kernel.riotapi.data.ChampionAPI.class);
            services.add(com.merakianalytics.kernel.riotapi.data.LeagueAPI.class);
            services.add(com.merakianalytics.kernel.riotapi.data.StaticDataAPI.class);
            services.add(com.merakianalytics.kernel.riotapi.data.StatusAPI.class);
            services.add(com.merakianalytics.kernel.riotapi.data.MatchAPI.class);
            services.add(com.merakianalytics.kernel.riotapi.data.SpectatorAPI.class);
            services.add(com.merakianalytics.kernel.riotapi.data.SummonerAPI.class);
            services.add(com.merakianalytics.kernel.riotapi.data.ThirdPartyCodeAPI.class);
        } else {
            services.add(com.merakianalytics.kernel.riotapi.dto.ChampionMasteryAPI.class);
            services.add(com.merakianalytics.kernel.riotapi.dto.ChampionAPI.class);
            services.add(com.merakianalytics.kernel.riotapi.dto.LeagueAPI.class);
            services.add(com.merakianalytics.kernel.riotapi.dto.StaticDataAPI.class);
            services.add(com.merakianalytics.kernel.riotapi.dto.StatusAPI.class);
            services.add(com.merakianalytics.kernel.riotapi.dto.MatchAPI.class);
            services.add(com.merakianalytics.kernel.riotapi.dto.SpectatorAPI.class);
            services.add(com.merakianalytics.kernel.riotapi.dto.SummonerAPI.class);
            services.add(com.merakianalytics.kernel.riotapi.dto.ThirdPartyCodeAPI.class);
        }

        // Swagger Documentation
        services.add(ApiListingResource.class);
        services.add(SwaggerSerializers.class);

        return services;
    }
}
