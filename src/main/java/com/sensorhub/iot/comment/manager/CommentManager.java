package com.sensorhub.iot.comment.manager;

import com.sensorhub.iot.article.domain.Article;
import com.sensorhub.iot.article.manager.ArticleManager;
import com.sensorhub.iot.comment.domain.Comment;
import com.sensorhub.iot.core.ext.store.MultipartFileResource;
import com.sensorhub.iot.core.ext.store.StoreConnector;
import com.sensorhub.iot.core.ext.store.StoreDTO;
import com.sensorhub.iot.core.hibernate.HibernateEntityDao;
import com.sensorhub.iot.core.page.Page;
import com.sensorhub.iot.core.util.StringUtils;
import com.sensorhub.iot.user.domain.UserInfo;
import com.sensorhub.iot.user.manager.UserInfoManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by admin on 2015/1/15.
 */
@Service
public class CommentManager extends HibernateEntityDao<Comment>
{
    private static Logger logger = LoggerFactory.getLogger(CommentManager.class);

    private StoreConnector storeConnector;

    private UserInfoManager userInfoManager;

    private ArticleManager articleManager;

    @Resource
    public void setArticleManager(ArticleManager articleManager)
    {
        this.articleManager = articleManager;
    }

    @Resource
    public void setUserInfoManager(UserInfoManager userInfoManager)
    {
        this.userInfoManager = userInfoManager;
    }

    @Resource
    public void setStoreConnector(StoreConnector storeConnector)
    {
        this.storeConnector = storeConnector;
    }

    public boolean addComment(long articleId,String message,long userId,MultipartFile file,long cID)
    {
        boolean result = false;
        try
        {
            UserInfo userInfo = userInfoManager.get(userId);
            Article article = articleManager.get(articleId);
            if (userInfo == null || article == null)
            {
                return  false;
            }
            Comment comment = new Comment();
            comment.setArticle(article);
            comment.setUserInfo(userInfo);
            comment.setCommentDate(new Date());
            comment.setContent(message);

            if (file != null&& StringUtils.isNotBlank(file.getOriginalFilename()))
            {
                logger.info("===============上传文件为空====================");
                StoreDTO storeDto = storeConnector.save("comment_image",
                        new MultipartFileResource(file),
                        file.getOriginalFilename());
                BigDecimal bg = new BigDecimal(file.getSize()/1024d/1024d);
                comment.setFileSize(bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                comment.setStoreLocation(storeDto.getKey());
            }

            if (cID != 0)
            {
                Comment parent = get(cID);
                comment.setParent(parent);
            }
            save(comment);

            result = true;
        }
        catch (Exception e)
        {
            result = false;
        }
        return result;
    }

    public List queryComment(Article article, int page, int rows)
    {
        String hql = "from Comment where article=? and status=? order by commentDate desc";
        Object[] params = new Object[]{article, 0};
        Page pageResult = pagedQuery(hql, page, rows, params);
        List<Comment> comments = (List<Comment>) pageResult.getResult();
        return comments;
    }

}
