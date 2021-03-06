package com.sensorhub.iot.core.ext.cache;

public interface Cache {
    <T> T get(String key);

    void set(String key, Object value);

    void remove(String key);
}
