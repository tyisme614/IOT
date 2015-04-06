package com.sensorhub.iot.user.support;

import com.sensorhub.iot.core.mapper.BeanMapper;
import com.sensorhub.iot.user.domain.UserInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2015/1/15.
 */
public class UserAccountWrapper extends UserInfo
{

    private transient BeanMapper beanMapper = new BeanMapper();

    public UserAccountWrapper()
    {

    }
    public UserAccountWrapper(UserInfo userAccount)
    {
        beanMapper.copy(userAccount,this);
    }

    public Map<String, Object> toMap()
    {
        Map<String, Object> map = new HashMap<String, Object>();
        this.copyUserBase(map, this);
        return map;
    }

    public void copyUserBase(Map<String, Object> map, UserInfo userAccount)
    {
        map.put("username", userAccount.getUserName());
        map.put("password", userAccount.getPassword());
        map.put("nickName", userAccount.getNickName());
        map.put("id", userAccount.getId());
        map.put("credits", userAccount.getCredits());
    }

}
