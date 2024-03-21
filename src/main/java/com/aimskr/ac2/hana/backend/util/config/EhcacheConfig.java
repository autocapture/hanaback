package com.aimskr.ac2.hana.backend.util.config;

import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@EnableCaching
public class EhcacheConfig {

    public static final String FLIGHT_CACHE = "flightCache";
    public static final String ARRIVAL_CACHE = "arrivalCache";
    public static final String DOCKEYWORD_CACHE = "docKeywordCache";
    public static final String PASSENGER_CACHE = "passengerCache";
    public static final String BIZINFO_CACHE = "bizInfoCache";
    public static final String ACCD_CACHE = "accdCache";


    private final javax.cache.configuration.Configuration<Object, Object> jCacheConfiguration;

    public EhcacheConfig() {
        this.jCacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                        ResourcePoolsBuilder.newResourcePoolsBuilder()
                                .heap(10000, EntryUnit.ENTRIES))
                .withSizeOfMaxObjectSize(1000, MemoryUnit.B)
                .withExpiry(ExpiryPolicyBuilder.timeToIdleExpiration(Duration.ofSeconds(300)))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(600))));
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(FLIGHT_CACHE, jCacheConfiguration);
            cm.createCache(ARRIVAL_CACHE, jCacheConfiguration);
            cm.createCache(DOCKEYWORD_CACHE, jCacheConfiguration);
            cm.createCache(PASSENGER_CACHE, jCacheConfiguration);
            cm.createCache(BIZINFO_CACHE, jCacheConfiguration);
            cm.createCache(ACCD_CACHE, jCacheConfiguration);
        };
    }

}
