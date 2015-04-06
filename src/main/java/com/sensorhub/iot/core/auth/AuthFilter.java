package com.sensorhub.iot.core.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by admin on 2015/3/3.
 */
public class AuthFilter implements Filter

{
    private static Logger logger = LoggerFactory.getLogger(AuthFilter.class);

    public void init(FilterConfig filterConfig) throws ServletException
    {
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws ServletException, IOException
    {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("_user_info") == null)
        {
            logger.info(request.getRequestURI());
            response.sendRedirect(request.getContextPath() + "/user/login.do");
            return;
        }
        filterChain.doFilter(request, response);
    }
}
