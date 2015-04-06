package com.sensorhub.iot.user.domain;

import com.sensorhub.iot.PipeRelateResource.domain.PipeRelateResourceInfo;
import com.sensorhub.iot.PipeRelateResource.domain.PipeRelateResourceRecord;
import com.sensorhub.iot.article.domain.Article;
import com.sensorhub.iot.comment.domain.Comment;
import com.sensorhub.iot.device.domain.DeviceInfo;
import com.sensorhub.iot.rely.domain.ReplyInfo;
import com.sensorhub.iot.service.domain.ServiceInfo;
import com.sensorhub.iot.service.domain.ServiceRegisterRecord;

import javax.lang.model.element.Name;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by admin on 2015/1/15.
 */
@Entity
@Table(name = "USER_ACCOUNT")
@SequenceGenerator(name = "SEQ_USER_ACCOUNT_ID", sequenceName = "SEQ_USER_ACCOUNT_ID", allocationSize = 1, initialValue = 1)
public class UserInfo implements Serializable
{

    private Long id;

    private String userName;

    private String password;

    private String nickName;

    private int credits;

    private int status=0;

    private Date registerDate;

    private Set<DeviceInfo> deviceInfos = new HashSet<DeviceInfo>(0);

    private Set<Comment> comments = new HashSet<Comment>(0);

    private Set<Article> articles = new HashSet<Article>(0);

    private Set<ReplyInfo> replyInfos = new HashSet<ReplyInfo>(0);

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
    public Set<ReplyInfo> getReplyInfos()
    {
        return replyInfos;
    }
    public void setReplyInfos(Set<ReplyInfo> replyInfos)
    {
        this.replyInfos = replyInfos;
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
    public String getUserName()
    {
        return userName;
    }
    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    @Column(name = "PASSWORD")
    public String getPassword()
    {
        return password;
    }
    public void setPassword(String password)
    {
        this.password = password;
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
