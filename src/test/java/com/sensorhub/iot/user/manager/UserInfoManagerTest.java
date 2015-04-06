package com.sensorhub.iot.user.manager;

import com.sensorhub.iot.user.domain.UserInfo;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import javax.annotation.Resource;

import java.util.List;

import static junit.framework.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring/applicationContext*.xml")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class })
public class UserInfoManagerTest {

    @Resource
    private UserInfoManager userInfoManager;

    @Test
    @DatabaseSetup(value = "classpath:UserInfo.xml", type = DatabaseOperation.CLEAN_INSERT)
    public void initUserAccount()
    {

    }

    @Test
    public void testAddUserAccount() throws Exception {
        UserInfo account = new UserInfo();
        account.setScopeId("I");
        account.setPassword("test");
        account.setUsername("test");
        userInfoManager.addUserAccount(account);
        account = userInfoManager.findUniqueBy("username","test");
        assertEquals("test",account.getUsername());
        userInfoManager.remove(account);
    }

    @Test
    @DatabaseSetup(value = "classpath:UserInfo.xml", type = DatabaseOperation.CLEAN_INSERT)
    public void testEditUserAccount() throws Exception {
        UserInfo account = userInfoManager.get(2L);
        account.setPassword("liuxin");
        userInfoManager.editUserAccount(account);
        account = userInfoManager.get(2L);
        assertEquals("liuxin",account.getPassword());
    }

    @Test
    @DatabaseSetup(value = "classpath:UserInfo.xml", type = DatabaseOperation.CLEAN_INSERT)
    public void testDelUserAccount() throws Exception {
        Integer count = userInfoManager.getCount()-1;
        userInfoManager.delUserAccount(2L);
        Integer now = userInfoManager.getCount();
        assertEquals(count.longValue(),now.longValue());
    }

    @Test
    @DatabaseSetup(value = "classpath:UserInfo.xml", type = DatabaseOperation.CLEAN_INSERT)
    public void testFindById() throws Exception {
        UserInfo account = userInfoManager.get(3L);
        assertEquals("Test2",account.getUsername());
    }

    @Test
    @DatabaseSetup(value = "classpath:UserInfo.xml", type = DatabaseOperation.CLEAN_INSERT)
    public void testFindByScope() throws Exception {
        List list = userInfoManager.findBy("scopeId","P");
        assertEquals(2,list.size());
    }
}