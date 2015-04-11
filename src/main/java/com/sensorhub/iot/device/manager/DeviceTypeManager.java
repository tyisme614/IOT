package com.sensorhub.iot.device.manager;

import com.sensorhub.iot.core.hibernate.HibernateEntityDao;
import com.sensorhub.iot.device.domain.DeviceInfo;
import com.sensorhub.iot.device.domain.DeviceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2015/1/15.
 */
@Service
public class DeviceTypeManager extends HibernateEntityDao<DeviceType>
{
    private static Logger logger = LoggerFactory.getLogger(DeviceTypeManager.class);


}
