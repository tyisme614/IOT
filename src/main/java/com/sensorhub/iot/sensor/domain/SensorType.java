package com.sensorhub.iot.sensor.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by admin on 2015/4/6.
 */
@Entity
@Table(name = "IOT_SENSOR_TYPE")
@SequenceGenerator(name = "SEQ_SENSOR_TYPE_ID", sequenceName = "SEQ_SENSOR_TYPE_ID", allocationSize = 1, initialValue = 1)
public class SensorType implements Serializable
{
    private long id;

    private String name;

    private String typeCode;

    private Set<SensorInfo> sensorInfos = new HashSet<SensorInfo>(0);

    private String memo;

    private int status=0;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SENSOR_TYPE_ID")
    @Column(name = "DBID")
    public long getId()
    {
        return id;
    }
    public void setId(long id)
    {
        this.id = id;
    }

    @Column(name = "SENSOR_TYPE_NAME")
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    @Column(name = "TYPECODE")
    public String getTypeCode()
    {
        return typeCode;
    }
    public void setTypeCode(String typeCode)
    {
        this.typeCode = typeCode;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sensorType")
    public Set<SensorInfo> getSensorInfos()
    {
        return sensorInfos;
    }
    public void setSensorInfos(Set<SensorInfo> sensorInfos)
    {
        this.sensorInfos = sensorInfos;
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
}
