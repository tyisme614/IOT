package com.sensorhub.iot.article.domain;

import com.sensorhub.iot.comment.domain.Comment;
import com.sensorhub.iot.user.domain.UserInfo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by admin on 2015/4/6.
 */

@Entity
@Table(name = "ARTICLE")
@SequenceGenerator(name = "SEQ_ARTICLE_INFO_ID", sequenceName = "SEQ_ARTICLE_INFO_ID", allocationSize = 1, initialValue = 1)
public class Article implements Serializable
{
    private long id;

    private String title;

    private String body;

    private String storeLocation;//图片存储路径

    private Set<Comment> comments = new HashSet<Comment>(0);

    /*
    0:正山
    1：删除
     */
    private int status=0;

    private UserInfo userInfo; //发帖人

    private int hits;//点赞次数

    private Date pubDate; //发帖时间

    private double fileSize;

    @Column(name="FILESIZE")
    public double getFileSize()
    {
        return fileSize;
    }
    public void setFileSize(double fileSize)
    {
        this.fileSize = fileSize;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ARTICLE_INFO_ID")
    @Column(name = "DBID")
    public long getId()
    {
        return id;
    }
    public void setId(long id)
    {
        this.id = id;
    }

    @Column(name = "TITLE")
    public String getTitle()
    {
        return title;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }

    @Column(name = "BODY")
    public String getBody()
    {
        return body;
    }
    public void setBody(String body)
    {
        this.body = body;
    }

    @Column(name = "STORELOCATION")
    public String getStoreLocation()
    {
        return storeLocation;
    }
    public void setStoreLocation(String storeLocation)
    {
        this.storeLocation = storeLocation;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "article")
    public Set<Comment> getComments()
    {
        return comments;
    }
    public void setComments(Set<Comment> comments)
    {
        this.comments = comments;
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
    @JoinColumn(name = "USER_ID")
    public UserInfo getUserInfo()
    {
        return userInfo;
    }
    public void setUserInfo(UserInfo userInfo)
    {
        this.userInfo = userInfo;
    }

    @Column(name = "HITS")
    public int getHits()
    {
        return hits;
    }
    public void setHits(int hits)
    {
        this.hits = hits;
    }

    @Column(name = "PUBDATE")
    public Date getPubDate()
    {
        return pubDate;
    }

    public void setPubDate(Date pubDate)
    {
        this.pubDate = pubDate;
    }
}
