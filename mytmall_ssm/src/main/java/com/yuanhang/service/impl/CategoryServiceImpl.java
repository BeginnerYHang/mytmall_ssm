package com.yuanhang.service.impl;

import com.yuanhang.mapper.CategoryMapper;
import com.yuanhang.pojo.Category;
import com.yuanhang.pojo.CategoryExample;
import com.yuanhang.pojo.Product;
import com.yuanhang.service.CategoryService;
import com.yuanhang.service.ProductService;
import com.yuanhang.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuanhang
 * @Description
 * @date 2020-05-28 17:47
 */
@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ProductService productService;

    @Override
    public List<Category> findAll() {
        CategoryExample example = new CategoryExample();
        example.setOrderByClause("id desc");
        List<Category> cs = categoryMapper.selectByExample(example);
        return cs;
    }

    @Override
    public Category findOne(int id) {
        Category c = categoryMapper.selectByPrimaryKey(id);
        fillProduct(c);
        return c;
    }

    @Override
    public void addCategory(Category category) {
        categoryMapper.insert(category);
    }

    @Override
    public void updateCategory(Category category) {
        categoryMapper.updateByPrimaryKeySelective(category);
    }

    @Override
    public void deleteCategory(int id) {
        categoryMapper.deleteByPrimaryKey(id);
    }


    public void fillProduct(Category category){
       category.setProducts(productService.findAll(category.getId()));
    }


    public void fillProduct(List<Category> cs){
        for (Category c : cs) {
            fillProduct(c);
        }
    }


    public void fillProductByRow(Category c){
        List<Product> ps = productService.findAll(c.getId());
        List<List<Product>> psByRow = new ArrayList<>();
        int count = 0;
        List<Product> tempPs = new ArrayList<>();
        for (Product p : ps) {
            tempPs.add(p);
            if (count % 8 == 0 && count > 7){
                psByRow.add(tempPs);
                tempPs.clear();
            }
            count ++;
        }
        psByRow.add(tempPs);
        c.setProductsByRow(psByRow);
    }

    public void fillProductByRow(List<Category> cs){
        for (Category c : cs) {
            fillProductByRow(c);
        }
    }
}
