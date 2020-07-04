package com.yuanhang.service.impl;

import com.yuanhang.mapper.OrderMapper;
import com.yuanhang.pojo.Order;
import com.yuanhang.pojo.OrderExample;
import com.yuanhang.pojo.OrderItem;
import com.yuanhang.pojo.UserExample;
import com.yuanhang.service.OrderItemService;
import com.yuanhang.service.OrderService;
import com.yuanhang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author yuanhang
 * @Description
 * @date 2020-06-04 8:20
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper mapper;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private UserService userService;

    @Override
    public Order findOne(int id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public Order findOneByUid(int uid) {
        OrderExample example = new OrderExample();
        example.createCriteria().andUidEqualTo(uid);
        List<Order> os = mapper.selectByExample(example);
        if (os.size() != 0){
            return os.get(0);
        }
        return null;
    }

    @Override
    public List<Order> findAll() {
        OrderExample example = new OrderExample();
        example.setOrderByClause("id desc");
        List<Order> os = mapper.selectByExample(example);
        fill(os);
        return os;
    }

    @Override
    public List<Order> findAllExcludeStatus(int uid,String status) {
        OrderExample example = new OrderExample();
        example.setOrderByClause("createDate desc");
        example.createCriteria().andUidEqualTo(uid).andStatusNotEqualTo(status);
        List<Order> os = mapper.selectByExample(example);
        for (Order order : os) {
            fill(order);
        }
        return os;
    }

    @Override
    public void update(Order order){
        mapper.updateByPrimaryKeySelective(order);
    }

    /**
     * 为购物车中的订单项绑定订单号用于生成订单(进行两次更新操作,需要进行事务控制)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackForClassName = {"Exception"})
    public float add(Order o, List<OrderItem> oi) {
        mapper.insert(o);
        float money = 0;
        for (OrderItem orderItem : oi) {
            orderItem.setOid(o.getId());
            orderItemService.update(orderItem);
            money += orderItem.getNumber() * orderItem.getProduct().getPromotePrice();
        }
        return money;
    }

    //为Order类中的非数据库表字段填充数据
    @Override
    public void fill(Order o){
        List<OrderItem> ois = orderItemService.findAll(o.getId());
        o.setOrderItems(ois);
        o.setUser(userService.findOne(o.getUid()));
    }

    public void fill(List<Order> os){
        for (Order order : os) {
            fill(order);
        }
    }
}
