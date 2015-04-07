package com.sensorhub.iot.article.manager;

import com.sensorhub.iot.article.domain.Article;
import com.sensorhub.iot.core.ext.store.MultipartFileResource;
import com.sensorhub.iot.core.ext.store.StoreConnector;
import com.sensorhub.iot.core.ext.store.StoreDTO;
import com.sensorhub.iot.core.hibernate.HibernateEntityDao;
import com.sensorhub.iot.core.util.StringUtils;
import com.sensorhub.iot.user.domain.UserInfo;
import com.sensorhub.iot.user.manager.UserInfoManager;
import org.hibernate.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * Created by admin on 2015/1/15.
 */
@Service
public class ArticleManager extends HibernateEntityDao<Article>
{
    private static Logger logger = LoggerFactory.getLogger(ArticleManager.class);

    private UserInfoManager userInfoManager;

    private StoreConnector storeConnector;

    @Resource
    public void setStoreConnector(StoreConnector storeConnector)
    {
        this.storeConnector = storeConnector;
    }

    @Resource
    public void setUserInfoManager(UserInfoManager userInfoManager)
    {
        this.userInfoManager = userInfoManager;
    }

    public boolean saveArticle(String title,String body,MultipartFile file,long userId)
    {
        try
        {
            UserInfo userInfo = userInfoManager.get(userId);
            if (userInfo == null)
            {
                return  false;
            }
            Article article = new Article();
            article.setBody(body);
            article.setTitle(title);

            if (file != null&& StringUtils.isNotBlank(file.getOriginalFilename()))
            {
                StoreDTO storeDto = storeConnector.save("article_image",
                        new MultipartFileResource(file),
                        file.getOriginalFilename());
                BigDecimal bg = new BigDecimal(file.getSize()/1024d/1024d);
                article.setFileSize(bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                article.setStoreLocation(storeDto.getKey());
            }
            save(article);
            return true;
        }
        catch (Exception e)
        {
            logger.info(e.getMessage());
            return false;
        }
    }


}
