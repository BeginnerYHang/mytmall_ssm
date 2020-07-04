package com.yuanhang.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yuanhang.pojo.Order;
import com.yuanhang.service.OrderService;
import com.yuanhang.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * @author yuanhang
 * @Description :订单管理后端只提供查询和发货两个功能,增删改由前端用户进行操作
 * @date 2020-06-04 8:17
 */
@Controller
@RequestMapping("")
public class OrderController {

    @Autowired
    private OrderService service;

    @RequestMapping("/admin_order_list")
    public String list(Page page, Model model){
        PageHelper.offsetPage(page.getStart(),page.getCount());
        List<Order> os = service.findAll();
        page.setTotal((int) new PageInfo<>(os).getTotal());
        model.addAttribute("os",os);
        model.addAttribute("page",page);
        return "admin/listOrder";
    }

    @RequestMapping("/admin_order_delivery")
    public String delivery(Order order){
        order.setDeliveryDate(new Date());
        order.setStatus(OrderService.WAIT_CONFIRM);
        service.update(order);
        return "redirect:admin_order_list";
    }

    @RequestMapping("/admin_order_deliveryajax")
    @ResponseBody
    public String deliveryAjax(Order order){
        order.setDeliveryDate(new Date());
        order.setStatus(OrderService.WAIT_CONFIRM);
        service.update(order);
        return "success";
    }
}
