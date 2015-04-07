package com.sensorhub.iot.comment.support;

import com.sensorhub.iot.comment.domain.Comment;
import com.sensorhub.iot.core.mapper.BeanMapper;
import com.sensorhub.iot.user.domain.UserInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2015/1/15.
 */
public class CommentWrapper extends Comment
{

    private transient BeanMapper beanMapper = new BeanMapper();

    public CommentWrapper()
    {

    }
    public CommentWrapper(Comment comment)
    {
        beanMapper.copy(comment,this);
    }

    public Map<String, Object> toMap()
    {
        Map<String, Object> map = new HashMap<String, Object>();
        this.copyCommentBase(map, this);
        return map;
    }

    public void copyCommentBase(Map<String, Object> map, Comment comment)
    {
        map.put("content", comment.getContent());
        map.put("storeLocation", comment.getStoreLocation());
        map.put("commentDate", comment.getCommentDate());
        map.put("commentUserName", comment.getUserInfo().getNickName());

    }

}
