package com.yuanhang.service;

import com.yuanhang.pojo.Product;
import com.yuanhang.pojo.PropertyValue;

import java.util.List;

/**
 * @author yuanhang
 * @Description :属性的管理在propertyService中已经提供,故只需要进行属性值操作即可(没有增加方法,故应该进行初始化)
 * @date 2020-06-02 9:01
 */
public interface PropertyValueService {
    void init(Product p);

    List<PropertyValue> findAll(int ptid);

    void updatePropertyValue(PropertyValue pv);

    /**
     * 通过属性ID和商品ID来获取属性值,从而进行初始化操作
     */
    PropertyValue findOne(int ptid,int pid);
}
