package com.sensorhub.iot.alarm.domain;

import com.sensorhub.iot.sensor.domain.SensorInfo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by admin on 2015/4/6.
 */

@Entity
@Table(name = "ALARM_INFO")
@SequenceGenerator(name = "SEQ_ALARM_INFO_ID", sequenceName = "SEQ_ALARM_INFO_ID", allocationSize = 1, initialValue = 1)
public class AlarmInfo implements Serializable
{
    private long id;

    private String content; //报警内容

    private Date collectDate; //报警采集时间

    private Date uploadDate; //报警上传时间

    /*
    0：未处理
    1：已推送
    2：已删除
     */
    private int status=0;

    private String currentValue;//报警超限的值

    private SensorInfo sensorInfo;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ALARM_INFO_ID")
    @Column(name = "DBID")
    public long getId()
    {
        return id;
    }
    public void setId(long id)
    {
        this.id = id;
    }

    @Column(name = "CONTENT")
    public String getContent()
    {
        return content;
    }
    public void setContent(String content)
    {
        this.content = content;
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

    @Column(name = "STATUS")
    public int getStatus()
    {
        return status;
    }
    public void setStatus(int status)
    {
        this.status = status;
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
}
