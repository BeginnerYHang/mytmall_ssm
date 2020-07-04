package com.yuanhang.service;

import com.yuanhang.pojo.Product;

import java.util.List;

/**
 * @author yuanhang
 * @Description
 * @date 2020-05-31 17:54
 */
public interface ProductService {
    Product findOne(int id);

    List<Product> findAll(int cid);

    void saveProduct(Product product);

    void deleteProduct(int id);

    void updateProduct(Product product);

    List<Product> search(String keyword);
}
