package com.yuanhang.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yuanhang.mapper.ProductImageMapper;
import com.yuanhang.pojo.Product;
import com.yuanhang.pojo.ProductImage;
import com.yuanhang.service.CategoryService;
import com.yuanhang.service.ProductImageService;
import com.yuanhang.service.ProductService;
import com.yuanhang.util.ImageUtil;
import com.yuanhang.util.Page;
import org.apache.commons.io.IOUtils;
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
 * @Description
 * @date 2020-06-01 10:45
 */
@Controller
@RequestMapping("")
public class ProductImageController {

    @Autowired
    private ProductImageService service;

    @Autowired
    private ProductService productService;


    @RequestMapping("/admin_productImage_list")
    public String list(int pid, Model model){
        Product p = productService.findOne(pid);
        model.addAttribute("p",p);
        List<ProductImage> pisSingle = service.findAll(pid,"type_single");
        model.addAttribute("pisSingle",pisSingle);
        List<ProductImage> pisDetail = service.findAll(pid,"type_detail");
        model.addAttribute("pisDetail",pisDetail);
        return "admin/listProductImage";
    }

    @RequestMapping("/admin_productImage_add")
    public String add(MultipartFile image, ProductImage productImage, HttpSession session) throws IOException {
        service.saveProductImage(productImage);
        String type = productImage.getType();
        String imgName = productImage.getId()+".jpg";
        String folderPath;
        String middleFolderPath = null,smallFolderPath = null;
        //如果是单个缩略图,应该有正常/中等/小三个大小
        //如果是详情图,保持正常大小即可
        if (ProductImageService.TYPE_SINGLE.equals(type)) {
            folderPath = session.getServletContext().getRealPath("/img/productSingle");
            middleFolderPath = session.getServletContext().getRealPath("/img/productSingle_middle");
            smallFolderPath = session.getServletContext().getRealPath("/img/productSingle_small");
        }else {
            folderPath = session.getServletContext().getRealPath("/img/productDetail");
        }
        File file = new File(folderPath,imgName);
        if (!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        File middleFile = new File(middleFolderPath,imgName);
        File smallFile = new File(smallFolderPath,imgName);
        image.transferTo(file);
        //保证图片为真正的JPG格式,而不仅仅是后缀名为.jpg
        BufferedImage bufferedImage = ImageUtil.change2jpg(file);
        ImageIO.write(bufferedImage,".jpg",file);
        if (ProductImageService.TYPE_SINGLE.equals(type)){
            ImageUtil.resizeImage(file,56,56,middleFile);
            ImageUtil.resizeImage(file,217,190,smallFile);
        }
        return "redirect:/admin_productImage_list?pid=" + productImage.getPid();
    }

    @RequestMapping("/admin_productImage_delete")
    public String delete(int id,int pid,HttpSession session){
        ProductImage productImage = service.findOne(id);
        String type = productImage.getType();
        String imgName = productImage.getId()+".jpg";
        String folderPath;
        String middleFolderPath = null,smallFolderPath = null;
        if (ProductImageService.TYPE_SINGLE.equals(type)) {
            folderPath = session.getServletContext().getRealPath("/img/productSingle");
            middleFolderPath = session.getServletContext().getRealPath("/img/productSingle_middle");
            smallFolderPath = session.getServletContext().getRealPath("/img/productSingle_small");
            new File(middleFolderPath,imgName).delete();
            new File(smallFolderPath,imgName).delete();
        }else {
            folderPath = session.getServletContext().getRealPath("/img/productDetail");
        }
        new File(folderPath,imgName).delete();
        //删除时不仅要删除数据库中的项，还应该删除服务器中的图片
        service.deleteProductImage(id);
        return "redirect:/admin_productImage_list?pid=" + pid;
    }
}
