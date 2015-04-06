package com.sensorhub.iot.user.web;


import com.sensorhub.iot.core.ext.export.Exportor;
import com.sensorhub.iot.core.hibernate.PropertyFilter;
import com.sensorhub.iot.core.mapper.BeanMapper;
import com.sensorhub.iot.core.spring.MessageHelper;
import com.sensorhub.iot.core.util.StringUtils;
import com.sensorhub.iot.status.LogTypeEnum;
import com.sensorhub.iot.sysLog.domain.LogInfo;
import com.sensorhub.iot.sysLog.manager.LogInfoManager;
import com.sensorhub.iot.user.domain.UserInfo;
import com.sensorhub.iot.user.manager.UserInfoManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2015/1/15.
 */
@Controller
@RequestMapping("user")
public class UserInfoController {
    private static Logger logger = LoggerFactory.getLogger(UserInfoController.class);

    private UserInfoManager userInfoManager;

    private Exportor exportor;

    private MessageHelper messageHelper;

    private LogInfoManager logInfoManager;

    private BeanMapper beanMapper = new BeanMapper();

    public MessageHelper getMessageHelper()
    {
        return messageHelper;
    }

    @Resource
    public void setMessageHelper(MessageHelper messageHelper)
    {
        this.messageHelper = messageHelper;
    }

    @Resource
    public void setLogInfoManager(LogInfoManager logInfoManager) {
        this.logInfoManager = logInfoManager;
    }

    @Resource
    public void setUserInfoManager(UserInfoManager userInfoManager) {
        this.userInfoManager = userInfoManager;
    }

    @Resource
    public void setExportor(Exportor exportor) {
        this.exportor = exportor;
    }


    @RequestMapping("user-checkUsername")
    @ResponseBody
    public boolean checkUsername(@RequestParam("username") String username,
                                 @RequestParam(value = "id", required = false) Long id)throws Exception
    {
        logger.info("enter check user name");
        String hql = "from UserInfo where username=?";
        Object[] params = {username};
        if (id != null) {
            hql = "from UserInfo where username=? and id<>?";
            params = new Object[]{username, id};
        }

        boolean result = userInfoManager.findUnique(hql, params) == null;

        return result;
    }

    @RequestMapping("user-info-register")
    public String registerUserInfo(){
        return "user/user-info-register";
    }

    @RequestMapping("add-user-info")
    @Transactional
    public String addUserInfo(@ModelAttribute UserInfo userInfo,
                              RedirectAttributes redirectAttributes)
    {
        try
        {
            userInfoManager.save(userInfo);

            LogInfo logInfo = new LogInfo();
            logInfo.setTitle("用户注册");
            logInfo.setContent(userInfo.getUsername() + "用户信息注册！");
            logInfo.setLogDate(new Date());
            logInfo.setLogType(LogTypeEnum.USER_INFO_REGISTER.ordinal());
            logInfo.setUserInfo(userInfo);
            logInfoManager.save(logInfo);

            messageHelper.addFlashMessage(redirectAttributes, "用户注册成功！");
            return "redirect:/user/login.do";
        }
        catch (Exception e)
        {
            messageHelper.addFlashMessage(redirectAttributes, "用户注册失败！");
            return "redirect:/user/user-info-register.do";
        }
    }

    /**
     * 用户信息保存或修改操作
     * 若userInfo中的id属性为空则执行保存操作，否则执行修改操作
     *
     * @return
     */
    @RequestMapping("user-info-edit")
    public String editUserInfo(HttpSession session,Model model)
    {
        Long userID =((UserInfo)session.getAttribute("_user_info")).getId();
        UserInfo info = userInfoManager.get(userID);
        if(null==info){
            info = new UserInfo();
        }
        model.addAttribute("model", info);
        return "user/user-info-edit";
    }

    @RequestMapping("user-info-save")
    @Transactional
    public String saveUserInfo(@ModelAttribute UserInfo info,Model model,HttpSession session)
    {
        try
        {
            LogInfo logInfo = new LogInfo();
            logInfo.setUserInfo((UserInfo) session.getAttribute("_user_info"));
            logInfo.setLogType(LogTypeEnum.USER_INFO_EDIT.ordinal());
            logInfo.setLogDate(new Date());
            logInfo.setContent(info.getUsername()+"用户信息被修改！");
            logInfo.setTitle("用户信息修改");
            logInfoManager.save(logInfo);

            userInfoManager.save(info);
            session.removeAttribute("_user_info");
            session.setAttribute("_user_info",info);
            model.addAttribute("model", info);
            messageHelper.addMessage(model,"用户信息修改成功！");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            messageHelper.addMessage(model,"用户修改失败");
        }
        return "user/user-info-edit";
    }

    /**
     * 系统登录
     * @param parameterMap
     * @param session
     * @return
     */
    @RequestMapping("user-info-login")
    @Transactional
    public String longInSystem(@RequestParam Map<String, Object> parameterMap,
                               HttpSession session,
                               RedirectAttributes redirectAttributes)
    {
        if(!StringUtils.isNotBlank((String) parameterMap.get("filter_EQS_username"))){
            messageHelper.addFlashMessage(redirectAttributes,"用户名或密码错误!");
            return "redirect:/user/login.do";
        }
        List<PropertyFilter> propertyFilters = PropertyFilter.buildFromMap(parameterMap);

        List list = userInfoManager.find(propertyFilters);
        if (list.size() == 1) {
            session.setAttribute("_user_info", list.get(0));

            LogInfo logInfo = new LogInfo();
            logInfo.setTitle("用户登录系统");
            UserInfo userInfo = (UserInfo) list.get(0);
            logInfo.setContent(userInfo.getUsername()+"用户登录了系统！");
            logInfo.setLogDate(new Date());
            logInfo.setLogType(LogTypeEnum.SYSTEM_LOGIN.ordinal());
            logInfo.setUserInfo(userInfo);
            logInfoManager.save(logInfo);
            return "redirect:../index.jsp";
        } else {
             messageHelper.addFlashMessage(redirectAttributes,"用户名或密码错误!");
            return "redirect:/user/login.do";
        }
    }

    /**
     * 退出系统
     */
    @RequestMapping("user-log-out")
    @Transactional
    public String logoutSystem(HttpSession session){
        UserInfo userInfo = (UserInfo) session.getAttribute("_user_info");
        LogInfo logInfo = new LogInfo();
        logInfo.setContent(userInfo.getUsername()+"退出系统！");
        logInfo.setTitle("系统退出");
        logInfo.setLogType(LogTypeEnum.SYSTEM_LOGOUT.ordinal());
        logInfo.setUserInfo(userInfo);
        logInfo.setLogDate(new Date());
        logInfoManager.save(logInfo);

        session.removeAttribute("_user_info");
        session.invalidate();

        return "redirect:/user/login.do";
    }

    @RequestMapping("login")
    public String loginSystem(){
        return "user/login";
    }
}
