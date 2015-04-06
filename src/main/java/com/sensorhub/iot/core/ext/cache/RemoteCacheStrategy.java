package com.sensorhub.iot.core.ext.cache;

public class RemoteCacheStrategy implements CacheStrategy {
    private Cache cache = new MapCache();

    public Cache getCache(String name) {
        return cache;
    }
}
