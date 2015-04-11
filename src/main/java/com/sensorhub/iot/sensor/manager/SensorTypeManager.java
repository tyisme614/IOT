package com.sensorhub.iot.sensor.manager;

import com.sensorhub.iot.core.hibernate.HibernateEntityDao;
import com.sensorhub.iot.device.domain.DeviceType;
import com.sensorhub.iot.sensor.domain.SensorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by admin on 2015/1/15.
 */
@Service
public class SensorTypeManager extends HibernateEntityDao<SensorType>
{
    private static Logger logger = LoggerFactory.getLogger(SensorTypeManager.class);


}
