package com.yuanhang.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yuanhang.exception.DeleteException;
import com.yuanhang.pojo.Category;
import com.yuanhang.service.CategoryService;
import com.yuanhang.util.ImageUtil;
import com.yuanhang.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author yuanhang
 * @Description : 分类请求控制器
 * @date 2020-05-28 17:48
 */
@Controller
@RequestMapping("")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @RequestMapping("/admin_category_list")
    public String findAll(Model model, Page page){
        //通过分页插件指定分页参数
        PageHelper.offsetPage(page.getStart(), page.getCount());
        List<Category> cs = service.findAll();
        int total = (int) new PageInfo<>(cs).getTotal();
        page.setTotal(total);
        model.addAttribute("cs",cs);
        model.addAttribute("page",page);
        return "admin/listCategory";
    }

    @RequestMapping("/admin_category_add")
    public String addCategory(Category c, MultipartFile image, HttpSession session) throws IOException {
        service.addCategory(c);
        String realPath = session.getServletContext().getRealPath("/img/category/");
        File imgFolder = new File(realPath);
        if (!imgFolder.exists()){
            imgFolder.mkdirs();
        }
        File imgFile = new File(imgFolder,c.getId() + ".jpg");
        image.transferTo(imgFile);
        //将图片统一设置为jpg格式
        BufferedImage img = ImageUtil.change2jpg(imgFile);
        ImageIO.write(img,"jpg", imgFile);
        return "redirect:/admin_category_list";
    }

    @RequestMapping("/admin_category_delete")
    public String removeCategory(int id) throws DeleteException{
        try {
            service.deleteCategory(id);
        }catch (Exception e){
            throw new DeleteException("删除该分类可能级联删除其下的产品、属性等，请谨慎操作");
        }
        return "redirect:/admin_category_list";
    }

    @RequestMapping("/admin_category_edit")
    public String editCategory(int id,Model model){
        Category c = service.findOne(id);
        model.addAttribute("c",c);
        return "admin/editCategory";
    }

    @RequestMapping("/admin_category_update")
    public String updateCategory(Category category,MultipartFile image,HttpSession session) throws IOException {
        service.updateCategory(category);
        String realPath = session.getServletContext().getRealPath("/img/category/");
        if (image != null && !image.isEmpty()){
            File file = new File(realPath,category.getId()+".jpg");
            //该方法会自动删除之前的文件
            image.transferTo(file);
            BufferedImage img = ImageUtil.change2jpg(file);
            ImageIO.write(img,"jpg",file);
        }
        return "redirect:admin_category_list";
    }
}
