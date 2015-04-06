package com.sensorhub.iot.core.ext.cache;

public interface CacheStrategy {
    Cache getCache(String name);
}
