package com.yuanhang.service;

import com.yuanhang.pojo.Order;
import com.yuanhang.pojo.OrderItem;

import java.util.List;

/**
 * @author yuanhang
 * @Description
 * @date 2020-06-04 8:19
 */
public interface OrderService {
    String WAIT_PAY = "waitPay";
    String WAIT_DELIVERY = "waitDelivery";
    String WAIT_CONFIRM = "waitConfirm";
    String WAIT_REVIEW = "waitReview";
    String FINISH = "finish";
    String DELETE = "delete";

    Order findOne(int id);

    Order findOneByUid(int uid);

    List<Order> findAll();

    /**
     * Description :获取订单时将用户所有状态不为status的订单
     * @param uid
     * @param status
     * @return java.util.List<com.yuanhang.pojo.Order>
     * @author yuanhang
     * @date 2020/6/17 9:16
     */
    List<Order> findAllExcludeStatus(int uid,String status);

    void update(Order o);

    float add(Order o , List<OrderItem> oi);

    void fill(Order o);
}
