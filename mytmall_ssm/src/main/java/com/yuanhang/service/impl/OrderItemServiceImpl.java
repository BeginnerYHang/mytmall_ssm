package com.yuanhang.service.impl;

import com.yuanhang.mapper.OrderItemMapper;
import com.yuanhang.mapper.OrderMapper;
import com.yuanhang.pojo.OrderItem;
import com.yuanhang.pojo.OrderItemExample;
import com.yuanhang.pojo.Product;
import com.yuanhang.service.OrderItemService;
import com.yuanhang.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yuanhang
 * @Description
 * @date 2020-06-04 9:17
 */
@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemMapper mapper;

    @Autowired
    private ProductService productService;

    @Override
    public OrderItem findOne(int oiid) {
        OrderItem oi = mapper.selectByPrimaryKey(oiid);
        oi.setProduct(productService.findOne(oi.getPid()));
        return oi;
    }

    @Override
    public List<OrderItem> findAll(int oid) {
        OrderItemExample example = new OrderItemExample();
        example.createCriteria().andOidEqualTo(oid);
        List<OrderItem> ois = mapper.selectByExample(example);
        for (OrderItem orderItem : ois) {
            orderItem.setProduct(productService.findOne(orderItem.getPid()));
        }
        return ois;
    }

    @Override
    public List<OrderItem> findAllByPid(int pid) {
        OrderItemExample example = new OrderItemExample();
        example.createCriteria().andPidEqualTo(pid);
        return  mapper.selectByExample(example);
    }

    @Override
    public List<OrderItem> findAllByUid(int uid) {
        OrderItemExample example = new OrderItemExample();
        example.createCriteria().andUidEqualTo(uid);
        List<OrderItem> ois = mapper.selectByExample(example);
        for (OrderItem orderItem : ois) {
            orderItem.setProduct(productService.findOne(orderItem.getPid()));
        }
        return ois;
    }

    @Override
    public void insert(OrderItem orderItem) {
        mapper.insert(orderItem);
    }

    @Override
    public void update(OrderItem orderItem) {
        mapper.updateByPrimaryKeySelective(orderItem);
    }

    @Override
    public void delete(int oiid) {
        mapper.deleteByPrimaryKey(oiid);
    }

}
