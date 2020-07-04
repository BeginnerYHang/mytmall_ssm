package com.yuanhang.controller;

import com.yuanhang.pojo.Product;
import com.yuanhang.pojo.PropertyValue;
import com.yuanhang.service.ProductService;
import com.yuanhang.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author yuanhang
 * @Description
 * @date 2020-06-02 9:32
 */
@Controller
public class PropertyValueController {

    @Autowired
    private PropertyValueService service;

    @Autowired
    private ProductService productService;

    @RequestMapping("/admin_propertyValue_edit")
    public String findAll(int pid,Model model){
        Product p = productService.findOne(pid);
        service.init(p);
        List<PropertyValue> pvs =  service.findAll(pid);
        model.addAttribute("pvs",pvs);
        model.addAttribute("p",p);
        return "admin/editPropertyValue";
    }

    @RequestMapping("/admin_propertyValue_update")
    @ResponseBody
    public String update(PropertyValue pv){
        service.updatePropertyValue(pv);
        return "success";
    }
}
