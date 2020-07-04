package com.yuanhang.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yuanhang.pojo.User;
import com.yuanhang.service.UserService;
import com.yuanhang.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author yuanhang
 * @Description :在后台不提供User的增删改方法,增和改操作应由前台用户完成，而用户信息为重要数据，故不提供删除操作
 * @date 2020-06-03 20:51
 */
@Controller
@RequestMapping("")
public class UserController {

    @Autowired
    private UserService service;

    @RequestMapping("/admin_user_list")
    public String list(Page page,Model model){
        PageHelper.offsetPage(page.getStart(),page.getCount());
        List<User> us = service.findAll();
        page.setTotal((int)new PageInfo<>(us).getTotal());
        model.addAttribute("us",us);
        model.addAttribute("page",page);
        return "admin/listUser";
    }
}
