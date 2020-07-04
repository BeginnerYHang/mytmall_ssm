package com.yuanhang.service;

import com.yuanhang.pojo.Property;

import java.util.List;

/**
 * @author yuanhang
 * @Description
 * @date 2020-05-31 15:15
 */
public interface PropertyService {

    List<Property> findAllByCid(int cid);

    Property findOne(int id);

    void save(Property p);

    void delete(int id);

    void update(Property p);
}
