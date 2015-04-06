package com.sensorhub.iot.rely.domain;

import com.sensorhub.iot.comment.domain.Comment;
import com.sensorhub.iot.user.domain.UserInfo;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by admin on 2015/4/6.
 */

@Entity
@Table(name = "REPLY_INFO")
@SequenceGenerator(name = "SEQ_REPLY_INFO_ID", sequenceName = "SEQ_REPLY_INFO_ID", allocationSize = 1, initialValue = 1)
public class ReplyInfo
{
    private long id;

    private String content;//恢复内容

    private Comment comment;

    private UserInfo userInfo;//回复人

    /*
    0:正常
    1：删除
     */
    private int status=0;

    private Date relyDate;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_REPLY_INFO_ID")
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


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMMENT_ID")
    public Comment getComment()
    {
        return comment;
    }
    public void setComment(Comment comment)
    {
        this.comment = comment;
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

    @Column(name = "STATUS")
    public int getStatus()
    {
        return status;
    }
    public void setStatus(int status)
    {
        this.status = status;
    }

    @Column(name = "RELYDATE")
    public Date getRelyDate()
    {
        return relyDate;
    }
    public void setRelyDate(Date relyDate)
    {
        this.relyDate = relyDate;
    }
}
