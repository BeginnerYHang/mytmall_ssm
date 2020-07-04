package com.yuanhang.service.impl;

import com.yuanhang.mapper.PropertyValueMapper;
import com.yuanhang.pojo.Product;
import com.yuanhang.pojo.Property;
import com.yuanhang.pojo.PropertyValue;
import com.yuanhang.pojo.PropertyValueExample;
import com.yuanhang.service.PropertyService;
import com.yuanhang.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yuanhang
 * @Description
 * @date 2020-06-02 9:02
 */
@Service
public class PropertyValueServiceImpl implements PropertyValueService {
    @Autowired
    private PropertyValueMapper mapper;

    @Autowired
    private PropertyService propertyService;

    @Override
    public void init(Product p) {
        //找出对应商品所属分类的所有属性,确保数据库表中有所有的属性对应关系(属性值可以为空)
        List<Property> ps = propertyService.findAllByCid(p.getCid());
        for (Property property : ps) {
            //数据库中没有对应的表项
            if (findOne(property.getId(),p.getId()) == null){
                PropertyValue pv = new PropertyValue();
                pv.setPtid(property.getId());
                pv.setPid(p.getId());
                mapper.insert(pv);
            }
        }
    }

    @Override
    public List<PropertyValue> findAll(int pid) {
        PropertyValueExample example = new PropertyValueExample();
        example.createCriteria().andPidEqualTo(pid);
        example.setOrderByClause("id desc");
        List<PropertyValue> pvs = mapper.selectByExample(example);
        for (PropertyValue pv : pvs) {
            pv.setProperty(propertyService.findOne(pv.getPtid()));
        }
        return pvs;
    }


    @Override
    public void updatePropertyValue(PropertyValue pv) {
        mapper.updateByPrimaryKeySelective(pv);
    }

    @Override
    public PropertyValue findOne(int ptid, int pid) {
        PropertyValueExample example = new PropertyValueExample();
        example.createCriteria().andPtidEqualTo(ptid).andPidEqualTo(pid);
        List<PropertyValue> pvs = mapper.selectByExample(example);
        //Mybatis中未查询到数据时返回空集合并不是null
        if (!pvs.isEmpty()){
            return pvs.get(0);
        }
        return null;
    }
}
