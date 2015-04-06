package com.sensorhub.iot.sensor.domain;

import com.sensorhub.iot.alarm.domain.AlarmInfo;
import com.sensorhub.iot.dataRecord.domain.HCHCRecord;
import com.sensorhub.iot.device.domain.DeviceInfo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by admin on 2015/4/6.
 */
@Entity
@Table(name = "SENSOR_INFO")
@SequenceGenerator(name = "SEQ_SENSOR_INFO_ID", sequenceName = "SEQ_SENSOR_INFO_ID", allocationSize = 1, initialValue = 1)
public class SensorInfo implements Serializable
{
    private long id;

    private String name;

    private String uuid;

    private Date registerDate;

    /*
    0：正常
    1：删除
    2：停用
     */
    private int status=0;

    private DeviceInfo deviceInfo;

    private String memo;

    private SensorType sensorType;

    private Set<AlarmInfo> alarmInfos = new HashSet<AlarmInfo>(0);

    private Set<HCHCRecord> hchcRecords = new HashSet<HCHCRecord>(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sensorInfo")
    public Set<HCHCRecord> getHchcRecords()
    {
        return hchcRecords;
    }
    public void setHchcRecords(Set<HCHCRecord> hchcRecords)
    {
        this.hchcRecords = hchcRecords;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sensorInfo")
    public Set<AlarmInfo> getAlarmInfos()
    {
        return alarmInfos;
    }
    public void setAlarmInfos(Set<AlarmInfo> alarmInfos)
    {
        this.alarmInfos = alarmInfos;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SENSOR_ID")
    public SensorType getSensorType()
    {
        return sensorType;
    }
    public void setSensorType(SensorType sensorType)
    {
        this.sensorType = sensorType;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SENSOR_INFO_ID")
    @Column(name = "DBID")
    public long getId()
    {
        return id;
    }
    public void setId(long id)
    {
        this.id = id;
    }

    @Column(name = "SENSORNAME")
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    @Column(name = "SENSOR_UUID")
    public String getUuid()
    {
        return uuid;
    }
    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }

    @Column(name = "REGISTERDATE")
    public Date getRegisterDate()
    {
        return registerDate;
    }
    public void setRegisterDate(Date registerDate)
    {
        this.registerDate = registerDate;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEVICE_ID")
    public DeviceInfo getDeviceInfo()
    {
        return deviceInfo;
    }
    public void setDeviceInfo(DeviceInfo deviceInfo)
    {
        this.deviceInfo = deviceInfo;
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
}
