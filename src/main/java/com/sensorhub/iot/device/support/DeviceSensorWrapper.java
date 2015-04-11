package com.sensorhub.iot.device.support;

import com.sensorhub.iot.core.mapper.BeanMapper;
import com.sensorhub.iot.core.util.DateUtils;
import com.sensorhub.iot.device.domain.DeviceInfo;
import com.sensorhub.iot.sensor.domain.SensorInfo;
import com.sensorhub.iot.user.domain.UserInfo;

import javax.mail.search.SearchTerm;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by admin on 2015/1/15.
 */
public class DeviceSensorWrapper
{
    private String dev_uuid;
    private String registerDate;
    private List<String> sensor_uuid;
    private String userId;

    public DeviceSensorWrapper()
    {

    }
    public DeviceSensorWrapper(DeviceInfo deviceInfo)
    {
        this.dev_uuid = deviceInfo.getUuid();
        this.registerDate = DateUtils.sdf_day_time.format(deviceInfo.getSyncDate());
        Set<SensorInfo> sensorInfos = deviceInfo.getSensorInfos();
        for (SensorInfo sensorInfo : sensorInfos)
        {
            this.sensor_uuid.add(sensorInfo.getUuid());
        }

        UserInfo userInfo = deviceInfo.getUserInfo();
        if (userInfo != null)
        {
            this.userId = userInfo.getId().toString();
        }

    }



}
