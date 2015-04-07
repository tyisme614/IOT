package com.sensorhub.iot.article.rs;

import com.sensorhub.iot.article.domain.Article;
import com.sensorhub.iot.article.manager.ArticleManager;
import com.sensorhub.iot.user.domain.UserInfo;
import com.sensorhub.iot.user.manager.UserInfoManager;
import com.sensorhub.iot.user.support.UserAccountWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

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
@Path("article")
public class ArticleResource
{
    private static Logger logger = LoggerFactory.getLogger(ArticleResource.class);
    private ArticleManager articleManager;

    @POST
    @Path("upload")
    @Produces(MediaType.APPLICATION_JSON)
    public Map publicArticle(@QueryParam("title") String title,
                             @QueryParam("body") String body,
                             @QueryParam("userId") String userId,
                             @QueryParam("file") MultipartFile file)
    {
        boolean result = articleManager.saveArticle(title, body, file, Long.parseLong(userId));
        Map map = new HashMap<String, Object>();
        if (result == true)
        {
            map.put("success", true);
            map.put("message","发帖成功");
        }
        else
        {
            map.put("success", false);
            map.put("message","发帖失败");
        }
        return map;
    }


    @Resource
    public void setArticleManager(ArticleManager articleManager)
    {
        this.articleManager = articleManager;
    }
}
