package com.sensorhub.iot.user.domain;

import com.sensorhub.iot.article.domain.Article;
import com.sensorhub.iot.comment.domain.Comment;
import com.sensorhub.iot.device.domain.DeviceInfo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by admin on 2015/1/15.
 */
@Entity
@Table(name = "IOT_USER_ACCOUNT")
@SequenceGenerator(name = "SEQ_USER_ACCOUNT_ID", sequenceName = "SEQ_USER_ACCOUNT_ID", allocationSize = 1, initialValue = 1)
public class UserInfo implements Serializable
{

    private Long id;

    private String username;

    private String pasword;

    private String nickName;

    private int credits;

    private int status=0;

    private Date registerDate;

    private Set<DeviceInfo> deviceInfos = new HashSet<DeviceInfo>(0);

    private Set<Comment> comments = new HashSet<Comment>(0);

    private Set<Article> articles = new HashSet<Article>(0);

    @Column(name="PASWORD")
    public String getPasword()
    {
        return pasword;
    }
    public void setPasword(String pasword)
    {
        this.pasword = pasword;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userInfo")
    public Set<Comment> getComments()
    {
        return comments;
    }
    public void setComments(Set<Comment> comments)
    {
        this.comments = comments;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userInfo")
    public Set<Article> getArticles()
    {
        return articles;
    }
    public void setArticles(Set<Article> articles)
    {
        this.articles = articles;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userInfo")
    public Set<DeviceInfo> getDeviceInfos()
    {
        return deviceInfos;
    }
    public void setDeviceInfos(Set<DeviceInfo> deviceInfos)
    {
        this.deviceInfos = deviceInfos;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USER_ACCOUNT_ID")
    @Column(name = "DBID")
    public Long getId()
    {
        return id;
    }
    public void setId(Long id)
    {
        this.id = id;
    }

    @Column(name = "USERNAME")
    public String getUsername()
    {
        return username;
    }
    public void setUsername(String username)
    {
        this.username = username;
    }


    @Column(name="NICKNAME")
    public String getNickName()
    {
        return nickName;
    }
    public void setNickName(String nickName)
    {
        this.nickName = nickName;
    }

    @Column(name = "CREDITS")
    public int getCredits()
    {
        return credits;
    }
    public void setCredits(int credits)
    {
        this.credits = credits;
    }

    /*
    0：正常
    1：删除
    2：停用
     */
    @Column(name = "STATUS")
    public int getStatus()
    {
        return status;
    }
    public void setStatus(int status)
    {
        this.status = status;
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


}
