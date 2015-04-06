package com.sensorhub.iot.device.domain;

import com.sensorhub.iot.sensor.domain.SensorInfo;
import com.sensorhub.iot.user.domain.UserInfo;
import sun.management.Sensor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by admin on 2015/1/15.
 */
@Entity
@Table(name = "DEVICE_INFO")
@SequenceGenerator(name = "SEQ_DEVICE_INFO_ID", sequenceName = "SEQ_DEVICE_INFO_ID", allocationSize = 1, initialValue = 1)
public class DeviceInfo implements Serializable
{
    private long id;

    private String name;

    private String uuid;

    private UserInfo userInfo;

    /*
      0:在用
      1：删除
      2：停用
       */
    private int status;

    private Date syncDate;//设备与android绑定后，同步到服务器的日期

    private DeviceType deviceType;

    private Set<SensorInfo> sensorInfos = new HashSet<SensorInfo>(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "deviceInfo")
    public Set<SensorInfo> getSensorInfos()
    {
        return sensorInfos;
    }
    public void setSensorInfos(Set<SensorInfo> sensorInfos)
    {
        this.sensorInfos = sensorInfos;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    public UserInfo getUserInfo()
    {
        return userInfo;
    }
    public void setUserInfo(UserInfo userInfo)
    {
        this.userInfo = userInfo;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEVICE_ID")
    public DeviceType getDeviceType()
    {
        return deviceType;
    }
    public void setDeviceType(DeviceType deviceType)
    {
        this.deviceType = deviceType;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DEVICE_INFO_ID")
    @Column(name = "DBID")
    public long getId()
    {
        return id;
    }
    public void setId(long id)
    {
        this.id = id;
    }

    @Column(name = "DEVICE_NAME")
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    @Column(name = "DEVICE_UUID")
    public String getUuid()
    {
        return uuid;
    }
    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }

    @Column(name = "STATUS")
    public int getStatus()
    {
        return status;
    }
    public void setStatus(int status)
    {
        this.status = status;
    }

    @Column(name = "SYNCDATE")
    public Date getSyncDate()
    {
        return syncDate;
    }
    public void setSyncDate(Date syncDate)
    {
        this.syncDate = syncDate;
    }
}
