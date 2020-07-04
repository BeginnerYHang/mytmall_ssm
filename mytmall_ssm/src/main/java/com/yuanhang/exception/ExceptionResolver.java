package com.yuanhang.exception;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Description:
 * @author yuanhang
 * @date 2020-06-24 10:53
 */
public class ExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        Exception exception;
        if (e instanceof DeleteException){
             exception = e;
        }else {
            exception = new Exception("服务器发现未知错误,请联系管理员解决（邮箱:1139050614@qq.com）");
        }
        ModelAndView mv = new ModelAndView();
        mv.addObject("ex",exception);
        mv.setViewName("exception");
        return mv;
    }
}
