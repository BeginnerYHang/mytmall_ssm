package com.yuanhang.interceptor;

import com.yuanhang.pojo.Category;
import com.yuanhang.pojo.OrderItem;
import com.yuanhang.pojo.User;
import com.yuanhang.service.CategoryService;
import com.yuanhang.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Description:定义该拦截器用于为分类框加上分类，购物车加上数量,变形金刚加上导航
 *
 * @author yuanhang
 * @date 2020-06-14 9:49
 */
public class OtherInterceptor implements HandlerInterceptor {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private OrderItemService orderItemService;
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        String contextPath = httpServletRequest.getContextPath();
        httpServletRequest.setAttribute("contextPath",contextPath+"/");
        List<Category> cs = categoryService.findAll();
        httpServletRequest.setAttribute("cs",cs);
        User user = (User) httpServletRequest.getSession().getAttribute("user");
        int cartTotalItemNumber = 0;
        if (user != null){
            List<OrderItem> ois = orderItemService.findAllByUid(user.getId());
            for (OrderItem orderItem : ois) {
                if (orderItem.getOid() == null){
                    cartTotalItemNumber += orderItem.getNumber();
                }
            }
        }
        System.out.println(httpServletRequest.getRequestURI() + "经过postHandle了");
        httpServletRequest.setAttribute("cartTotalItemNumber",cartTotalItemNumber);
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
