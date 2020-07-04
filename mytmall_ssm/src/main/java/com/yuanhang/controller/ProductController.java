package com.yuanhang.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yuanhang.pojo.Category;
import com.yuanhang.pojo.Product;
import com.yuanhang.service.CategoryService;
import com.yuanhang.service.ProductService;
import com.yuanhang.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

/**
 * @author yuanhang
 * @Description
 * @date 2020-05-31 17:53
 */
@Controller
@RequestMapping("")
public class ProductController {
    @Autowired
    private ProductService service;

    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/admin_product_list")
    public String list(int cid,Model model,Page page){
        Category category = categoryService.findOne(cid);
        PageHelper.offsetPage(page.getStart(),page.getCount());
        List<Product> ps = service.findAll(cid);
        int total = (int) new PageInfo<>(ps).getTotal();
        page.setTotal(total);
        page.setParam("&cid=" + cid);
        model.addAttribute("c",category);
        model.addAttribute("ps",ps);
        return "admin/listProduct";
    }

    @RequestMapping("/admin_product_add")
    public String add(Product product){
        product.setCreateDate(new Date());
        service.saveProduct(product);
        return "redirect:/admin_product_list?cid=" + product.getCid();
    }

    @RequestMapping("/admin_product_delete")
    public String delete(int id,int cid){
        service.deleteProduct(id);
        return "redirect:/admin_product_list?cid=" + cid;
    }

    @RequestMapping("/admin_product_edit")
    public String edit(int id,Model model){
        //在Service层获取到的Product直接带有分类信息
        Product p = service.findOne(id);;
        model.addAttribute("p",p);
        return "admin/editProduct";
    }

    @RequestMapping("/admin_product_update")
    public String update(Product product){
        service.updateProduct(product);
        return "redirect:/admin_product_list?cid=" + product.getCid();
    }
}
