package com.sensorhub.iot.user.rs;

import com.sensorhub.iot.core.util.BaseDTO;
import com.sensorhub.iot.core.util.StringUtils;
import com.sensorhub.iot.user.domain.UserInfo;
import com.sensorhub.iot.user.manager.UserInfoManager;
import com.sensorhub.iot.user.support.UserAccountWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2015/1/15.
 */
@Component
@Path("user")
public class UserAccountResource
{
    private static Logger logger = LoggerFactory.getLogger(UserAccountResource.class);
    private UserInfoManager userInfoManager;

    @GET
    @Path("login")
    @Produces(MediaType.APPLICATION_JSON)
    public Map exists(@QueryParam("userName") String userName,
                          @QueryParam("password") String password) {
        Map map = new HashMap<String, Object>();
        try
        {
            UserInfo userInfo = userInfoManager.login(userName,password);
            if (userInfo == null)
            {
                map.put("success", false);
                map.put("message","登录用户名或密码错误");
                return  map;
            }
            map.put("success", true);
            map.put("message", new UserAccountWrapper(userInfo).toMap());
        }
        catch (Exception e)
        {
            map.put("success", false);
            map.put("message", e.getMessage());
        }
        return  map;

    }

    @POST
    @Path("register")
    @Produces(MediaType.APPLICATION_JSON)
    public Map register(@QueryParam("userName") String userName,
                            @QueryParam("password") String password,
                            @QueryParam("nickName") String nickName) {
        Map map = new HashMap<String, Object>();
        try
        {
            if (!userInfoManager.checkUserName(userName))
            {
                map.put("success", false);
                map.put("message","改账号已注册");
                return map;
            }
            UserInfo userInfo = new UserInfo();
            userInfo.setUserName(userName);
            userInfo.setNickName(nickName);
            userInfo.setPassword(password);
            userInfo.setRegisterDate(new Date());
            userInfo.setCredits(1); //默认级别为1级

            userInfoManager.addUserAccount(userInfo);
            map.put("success", true);
            map.put("message","注册成功");
        }
        catch (Exception e)
        {
            logger.info(e.getMessage());
            map.put("success", false);
            map.put("message",e.getMessage());
        }
        return map;
    }

    @Resource
    public void setUserInfoManager(UserInfoManager userInfoManager)
    {
        this.userInfoManager = userInfoManager;
    }
}
