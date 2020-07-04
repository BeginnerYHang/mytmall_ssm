package com.yuanhang.service;

import com.yuanhang.pojo.Product;
import com.yuanhang.pojo.ProductImage;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author yuanhang
 * @Description
 * @date 2020-06-01 10:05
 */
public interface ProductImageService {
     String TYPE_SINGLE = "type_single";
     String TYPE_DETAIL = "type_detail";

     ProductImage findOne(int id);

     List<ProductImage> findAll(int pid,String type);

     void saveProductImage(ProductImage productImage);

     void deleteProductImage(int id);

}
