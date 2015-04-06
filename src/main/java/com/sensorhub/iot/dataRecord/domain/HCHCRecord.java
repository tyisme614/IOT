package com.sensorhub.iot.dataRecord.domain;

import com.sensorhub.iot.sensor.domain.SensorInfo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by admin on 2015/4/6.
 */
@Entity
@Table(name = "HCHC_RECORD")
@SequenceGenerator(name = "SEQ_HCHC_RECORD_ID", sequenceName = "SEQ_HCHC_RECORD_ID", allocationSize = 1, initialValue = 1)
public class HCHCRecord implements Serializable
{
    private long id;

    private String currentValue;

    private SensorInfo sensorInfo;

    /*
    0:正常
    1：删除
     */
    private int status;

    private Date collectDate; //采集时间

    private Date uploadDate;  //发送时间

    private String config; //里面存放设备配置信息（采集间隔，发送间隔等）

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_HCHC_RECORD_ID")
    @Column(name = "DBID")
    public long getId()
    {
        return id;
    }
    public void setId(long id)
    {
        this.id = id;
    }

    @Column(name = "CURRENTVALUE")
    public String getCurrentValue()
    {
        return currentValue;
    }
    public void setCurrentValue(String currentValue)
    {
        this.currentValue = currentValue;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SENSOR_ID")
    public SensorInfo getSensorInfo()
    {
        return sensorInfo;
    }
    public void setSensorInfo(SensorInfo sensorInfo)
    {
        this.sensorInfo = sensorInfo;
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

    @Column(name = "COLLECTDATE")
    public Date getCollectDate()
    {
        return collectDate;
    }
    public void setCollectDate(Date collectDate)
    {
        this.collectDate = collectDate;
    }

    @Column(name = "UPLOADDATE")
    public Date getUploadDate()
    {
        return uploadDate;
    }
    public void setUploadDate(Date uploadDate)
    {
        this.uploadDate = uploadDate;
    }

    @Column(name = "CONFIG")
    public String getConfig()
    {
        return config;
    }
    public void setConfig(String config)
    {
        this.config = config;
    }
}
