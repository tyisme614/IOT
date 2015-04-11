package com.sensorhub.iot.device.manager;

import com.sensorhub.iot.core.hibernate.HibernateEntityDao;
import com.sensorhub.iot.device.domain.DeviceInfo;
import com.sensorhub.iot.device.domain.DeviceType;
import com.sensorhub.iot.sensor.domain.SensorInfo;
import com.sensorhub.iot.sensor.domain.SensorType;
import com.sensorhub.iot.sensor.manager.SensorManager;
import com.sensorhub.iot.sensor.manager.SensorTypeManager;
import com.sensorhub.iot.user.domain.UserInfo;
import com.sensorhub.iot.user.manager.UserInfoManager;
import org.hibernate.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.UserRoleAuthorizationInterceptor;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by admin on 2015/1/15.
 */
@Service
public class DeviceManager extends HibernateEntityDao<DeviceInfo>
{
    private static Logger logger = LoggerFactory.getLogger(DeviceManager.class);

    private DeviceTypeManager deviceTypeManager;

    private SensorManager sensorManager;

    private SensorTypeManager sensorTypeManager;

    private UserInfoManager userInfoManager;

    @Resource
    public void setUserInfoManager(UserInfoManager userInfoManager)
    {
        this.userInfoManager = userInfoManager;
    }

    @Resource
    public void setSensorManager(SensorManager sensorManager)
    {
        this.sensorManager = sensorManager;
    }

    @Resource
    public void setSensorTypeManager(SensorTypeManager sensorTypeManager)
    {
        this.sensorTypeManager = sensorTypeManager;
    }

    @Resource
    public void setDeviceTypeManager(DeviceTypeManager deviceTypeManager)
    {
        this.deviceTypeManager = deviceTypeManager;
    }

    public DeviceInfo registerDeviceWithoutUserName(String dev_uuid ,String typeCode, List<String> sensors)
    {
        DeviceInfo deviceInfo = findUniqueBy("uuid", dev_uuid);
        if (deviceInfo != null)
        {
            logger.info("设备ID" + dev_uuid+"已注册");
            return null;
        }

        DeviceType deviceType = deviceTypeManager.findUniqueBy("typeCode", typeCode);
        DeviceInfo newDev = new DeviceInfo();
        newDev.setUuid(dev_uuid);
        newDev.setDeviceType(deviceType);
        newDev.setSyncDate(new Date());

        Set<SensorInfo> sensorInfos = new HashSet<SensorInfo>(0);
        for (String sensor : sensors)
        {
            String[] ss = sensor.split(",");
            String sensorTypeCode = ss[0];
            String sensorUUID = ss[1];

            SensorType sensorType = sensorTypeManager.findUniqueBy("typeCode", sensorTypeCode);
            if (sensorType != null)
            {
                SensorInfo sensorInfo = sensorManager.findUniqueBy("uuid", sensorUUID);
                if (sensorInfo == null)
                {
                    SensorInfo sensorInfo1 = new SensorInfo();
                    sensorInfo1.setUuid(sensorUUID);
                    sensorInfo1.setDeviceInfo(newDev);
                    sensorInfo1.setRegisterDate(new Date());
                    sensorInfo1.setSensorType(sensorType);
                    sensorManager.save(sensorInfo1);
                    sensorInfos.add(sensorInfo1);
                }
            }
        }
        newDev.setSensorInfos(sensorInfos);
        this.save(newDev);
        return newDev;
    }


    public UserInfo registerDeviceWithUserName(String userId,List<String>uuids)
    {
        UserInfo userInfo = userInfoManager.get(Long.parseLong(userId));
        if (userInfo == null)
        {
            return null;
        }
        Set<DeviceInfo> deviceInfos = new HashSet<DeviceInfo>(0);
        for (String uuid : uuids)
        {
            DeviceInfo deviceInfo = findUniqueBy("uuid", uuid);
            deviceInfos.add(deviceInfo);
        }
        userInfo.setDeviceInfos(deviceInfos);
        userInfoManager.save(userInfo);
        return userInfo;
    }

    public Set<DeviceInfo> getDevicesByUserId(String userId)
    {
        UserInfo userInfo = userInfoManager.get(Long.parseLong(userId));
        return userInfo.getDeviceInfos();
    }

}
