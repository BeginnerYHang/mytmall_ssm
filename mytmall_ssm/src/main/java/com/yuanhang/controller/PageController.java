package com.yuanhang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Description: 用来访问页面
 *
 * @author yuanhang
 * @date 2020-06-07 8:24
 */
@Controller
@RequestMapping("")
public class PageController {

    @RequestMapping("/registerPage")
    public String registerPage(){
        return "fore/register";
    }

    @RequestMapping("/registerSuccess")
    public String registerSuccessPage(){
        return "fore/registerSuccess";
    }

    @RequestMapping("/loginPage")
    public String loginPage(){
        return "fore/login";
    }
}
