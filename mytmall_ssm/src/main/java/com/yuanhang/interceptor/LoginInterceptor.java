package com.yuanhang.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Description:登录状态拦截器,拦截未登录不能访问的controller方法
 * 需要在springmvc.xml方法中配置该拦截器
 * @author yuanhang
 * @date 2020-06-14 8:49
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String[] noNeedAuthPath = new String[]{
                "home",
                "category",
                "register",
                "login",
                "search",
                "product",
                "loginAjax",
                "checkLogin",
                "logout"
        };
        String contextPath = httpServletRequest.getSession().getServletContext().getContextPath();
        String uri = httpServletRequest.getRequestURI();
        String path = uri.substring(uri.lastIndexOf('/')).substring(5);
        System.out.println(path);
        for (String item : noNeedAuthPath) {
            if (item.equals(path)){
                return true;
            }
        }
        //不在不需要登录中的路径且未登录
        if (httpServletRequest.getSession().getAttribute("user") == null){
            httpServletResponse.sendRedirect(contextPath + "/loginPage");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
