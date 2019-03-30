package com.hd.epac.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;

@Component
public class BaseInterceptor implements HandlerInterceptor {
    private static final Logger logger = Logger.getLogger("BaseInterceptor");
    private static final String loginUrl = "/";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        //1、请求到登录页面 放行
        if (request.getServletPath().startsWith(loginUrl)) {
            return true;
        }
        //2、如果用户已经登录 放行
        if (request.getSession().getAttribute("userInfo") != null) {
            return true;
        }
        //3、非法请求 即这些请求需要登录后才能访问 重定向到登录页面
        response.sendRedirect(request.getContextPath() + loginUrl);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView modelAndView) throws Exception {}

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception e) throws Exception {}
}
