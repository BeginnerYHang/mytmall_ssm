package com.yuanhang.service.impl;

import com.yuanhang.mapper.ProductImageMapper;
import com.yuanhang.pojo.ProductImage;
import com.yuanhang.pojo.ProductImageExample;
import com.yuanhang.service.ProductImageService;
import com.yuanhang.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yuanhang
 * @Description
 * @date 2020-06-01 10:05
 */
@Service
public class ProductImageServiceImpl implements ProductImageService {

    @Autowired
    private ProductImageMapper mapper;

    @Override
    public ProductImage findOne(int id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public List<ProductImage> findAll(int pid,String type) {
        ProductImageExample example = new ProductImageExample();
        example.createCriteria().andPidEqualTo(pid).andTypeEqualTo(type);
        return mapper.selectByExample(example);
    }

    @Override
    public void saveProductImage(ProductImage productImage) {
        mapper.insert(productImage);
    }

    @Override
    public void deleteProductImage(int id) {
        mapper.deleteByPrimaryKey(id);
    }
}
