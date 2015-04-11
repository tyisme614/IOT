package com.sensorhub.iot.article.manager;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.sensorhub.iot.article.domain.Article;
import com.sensorhub.iot.user.domain.UserInfo;
import com.sensorhub.iot.user.manager.UserInfoManager;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import javax.annotation.Resource;
import static junit.framework.Assert.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring/applicationContext*.xml")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class })
public class ArticleManagerTest
{

    private ArticleManager articleManager;

    private UserInfoManager userInfoManager;

    @Resource
    public void setUserInfoManager(UserInfoManager userInfoManager)
    {
        this.userInfoManager = userInfoManager;
    }

    @Resource
    public void setArticleManager(ArticleManager articleManager)
    {
        this.articleManager = articleManager;
    }


    @Test
    public void testSaveArticle() throws Exception
    {
        UserInfo userInfo = new UserInfo();
        userInfo.setNickName("zhangfan");
        userInfoManager.save(userInfo);
        UserInfo userInfo1 = userInfoManager.findUniqueBy("nickName", "zhangfan");

        boolean result = articleManager.saveArticle("标题1", "内容", null, userInfo1.getId());
        assertEquals(true, result);
        Article article = articleManager.findUniqueBy("title", "标题1");
        assertEquals("内容", article.getBody());


    }



}