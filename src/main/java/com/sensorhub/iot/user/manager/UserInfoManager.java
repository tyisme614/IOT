package com.sensorhub.iot.user.manager;

import com.sensorhub.iot.core.hibernate.HibernateEntityDao;
import com.sensorhub.iot.user.domain.UserInfo;
import org.hibernate.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2015/1/15.
 */
@Service
public class UserInfoManager extends HibernateEntityDao<UserInfo>
{
    private static Logger logger = LoggerFactory.getLogger(UserInfoManager.class);

    public void  addUserAccount(UserInfo account)
    {
        this.getSession().save(account);
    }

    public boolean checkUserName(String userName)
    {
        logger.info(" ============check user name============");
        String hql = "from UserInfo where username=? and status=?";
        Object[] params = {userName,0};
        boolean result = findUnique(hql, params) == null;
        return result;
    }

    public UserInfo login(String userName, String password)
    {
        logger.info("enter check user name");

        String hql = "from UserInfo where username=? and pasword=? and status=?";
        UserInfo userInfo = findUnique(hql, userName, password, 0);
        return userInfo;
    }


}
