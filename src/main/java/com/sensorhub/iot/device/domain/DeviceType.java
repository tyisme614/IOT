package com.sensorhub.iot.device.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by admin on 2015/4/6.
 */
@Entity
@Table(name = "IOT_DEVICE_TYPE")
@SequenceGenerator(name = "SEQ_DEVICE_TYPE_ID", sequenceName = "SEQ_DEVICE_TYPE_ID", allocationSize = 1, initialValue = 1)
public class DeviceType implements Serializable
{
    private long id;

    private String name;

    private String typeCode;

    private String memo;

    /*
    0：在用
    1：删除
    2：停用
     */
    private int status;

    private Set<DeviceInfo> deviceInfos = new HashSet<DeviceInfo>(0);

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DEVICE_TYPE_ID")
    @Column(name = "DBID")
    public long getId()
    {
        return id;
    }
    public void setId(long id)
    {
        this.id = id;
    }

    @Column(name = "DEVICE_TYPE_NAME")
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    @Column(name = "TYPE_CODE")
    public String getTypeCode()
    {
        return typeCode;
    }
    public void setTypeCode(String typeCode)
    {
        this.typeCode = typeCode;
    }

    @Column(name = "MEMO")
    public String getMemo()
    {
        return memo;
    }
    public void setMemo(String memo)
    {
        this.memo = memo;
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "deviceType")
    public Set<DeviceInfo> getDeviceInfos()
    {
        return deviceInfos;
    }

    public void setDeviceInfos(Set<DeviceInfo> deviceInfos)
    {
        this.deviceInfos = deviceInfos;
    }
}
