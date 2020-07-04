package com.yuanhang.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yuanhang.pojo.Category;
import com.yuanhang.pojo.Property;
import com.yuanhang.service.CategoryService;
import com.yuanhang.service.PropertyService;
import com.yuanhang.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author yuanhang
 * @Description
 * @date 2020-05-31 15:11
 */
@Controller
@RequestMapping("")
public class PropertyController {
    @Autowired
    private PropertyService service;
    @Autowired
    private CategoryService categoryService;

    @RequestMapping("admin_property_list")
    public String findAll(int cid, Model model,Page page){
        Category c = categoryService.findOne(cid);
        PageHelper.offsetPage(page.getStart(),page.getCount());
        List<Property> ps = service.findAllByCid(cid);
        int total = (int) new PageInfo<>(ps).getTotal();
        page.setTotal(total);
        page.setParam("&cid="+ c.getId());
        model.addAttribute("ps",ps);
        model.addAttribute("c",c);
        model.addAttribute("page",page);
        return "admin/listProperty";
    }

    @RequestMapping("admin_property_edit")
    public String editProperty(int id,Model model){
        Property p = service.findOne(id);
        Category category = categoryService.findOne(p.getCid());
        p.setCategory(category);
        model.addAttribute("p",p);
        return "admin/editProperty";
    }


    @RequestMapping("admin_property_add")
    public String addProperty(Property property){
        service.save(property);
        return "redirect:/admin_property_list?cid=" + property.getCid();
    }

    @RequestMapping("admin_property_delete")
    public String deleteProperty(Property property){
        service.delete(property.getId());
        return "redirect:/admin_property_list?cid=" + property.getCid();
    }

    @RequestMapping("admin_property_update")
    public String updateProperty(Property property){
        service.update(property);
        return "redirect:/admin_property_list?cid=" + property.getCid();
    }
}
