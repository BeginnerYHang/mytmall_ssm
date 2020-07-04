package com.yuanhang.service.impl;

import com.yuanhang.mapper.PropertyMapper;
import com.yuanhang.pojo.Property;
import com.yuanhang.pojo.PropertyExample;
import com.yuanhang.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yuanhang
 * @Description
 * @date 2020-05-31 15:15
 */
@Service
public class PropertyServiceImpl implements PropertyService {
    @Autowired
    private PropertyMapper mapper;

    @Override
    public List<Property> findAllByCid(int cid) {
        PropertyExample example = new PropertyExample();
        example.createCriteria().andCidEqualTo(cid);
        return mapper.selectByExample(example);
    }

    @Override
    public Property findOne(int id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public void save(Property p) {
        mapper.insert(p);
    }

    @Override
    public void delete(int id) {
        mapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Property p) {
        mapper.updateByPrimaryKeySelective(p);
    }
}
