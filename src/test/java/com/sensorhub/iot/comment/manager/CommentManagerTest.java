package com.sensorhub.iot.comment.manager;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.sensorhub.iot.article.domain.Article;
import com.sensorhub.iot.article.manager.ArticleManager;
import com.sensorhub.iot.comment.domain.Comment;
import junit.framework.TestCase;
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
public class CommentManagerTest
{
    private CommentManager commentManager;

    private ArticleManager articleManager;

    @Resource
    public void setArticleManager(ArticleManager articleManager)
    {
        this.articleManager = articleManager;
    }

    @Resource
    public void setCommentManager(CommentManager commentManager)
    {
        this.commentManager = commentManager;
    }

    @Test
    @DatabaseSetup(value = "classpath:DB.xml", type = DatabaseOperation.CLEAN_INSERT)
    public void testAddComment() throws Exception
    {
        boolean result = commentManager.addComment((long)1, "张帆的评论", (long)1, null, (long)2);
        assertEquals(true, result);
        Comment comment = commentManager.findUniqueBy("content","张帆的评论");
        System.out.println(comment.getContent());
        assertEquals("张帆的评论", comment.getContent());
    }

    @Test
    @DatabaseSetup(value = "classpath:DB.xml", type = DatabaseOperation.CLEAN_INSERT)
    public void testQueryComment() throws Exception
    {
        Article article = articleManager.get((long)1);
        List comments = commentManager.queryComment(article, 1, 10);
        assertEquals(4, comments.size());
    }
}