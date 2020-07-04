package com.yuanhang.service;

import com.yuanhang.pojo.OrderItem;

import java.util.List;

/**
 * @author yuanhang
 * @Description
 * @date 2020-06-04 9:17
 */
public interface OrderItemService {

    OrderItem findOne(int oiid);

    List<OrderItem> findAll(int oid);

    List<OrderItem> findAllByPid(int pid);

    List<OrderItem> findAllByUid(int uid);

    void insert(OrderItem orderItem);

    void update(OrderItem orderItem);

    void delete(int oiid);
}
