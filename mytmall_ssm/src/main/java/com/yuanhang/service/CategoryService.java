package com.yuanhang.service;

import com.yuanhang.pojo.Category;
import com.yuanhang.util.Page;

import java.util.List;

/**
 * @author yuanhang
 * @Description
 * @date 2020-05-28 17:47
 */
public interface CategoryService {
    List<Category> findAll();

    Category findOne(int id);

    void addCategory(Category category);

    void updateCategory(Category category);

    void deleteCategory(int id);

}
