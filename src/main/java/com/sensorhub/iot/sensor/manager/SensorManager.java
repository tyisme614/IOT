package com.sensorhub.iot.sensor.manager;

import com.sensorhub.iot.core.hibernate.HibernateEntityDao;
import com.sensorhub.iot.device.domain.DeviceInfo;
import com.sensorhub.iot.device.domain.DeviceType;
import com.sensorhub.iot.sensor.domain.SensorInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by admin on 2015/1/15.
 */
@Service
public class SensorManager extends HibernateEntityDao<SensorInfo>
{
    private static Logger logger = LoggerFactory.getLogger(SensorManager.class);



}
