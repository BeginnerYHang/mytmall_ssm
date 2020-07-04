package com.yuanhang.service.impl;

import com.yuanhang.mapper.UserMapper;
import com.yuanhang.pojo.User;
import com.yuanhang.pojo.UserExample;
import com.yuanhang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yuanhang
 * @Description
 * @date 2020-06-03 20:47
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper mapper;

    @Override
    public List<User> findAll() {
        UserExample example = new UserExample();
        return mapper.selectByExample(example);
    }

    @Override
    public User findOne(int id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean isExist(String username) {
        UserExample example = new UserExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<User> us = mapper.selectByExample(example);
        return !us.isEmpty();
    }

    @Override
    public void add(User user) {
        mapper.insert(user);
    }

    @Override
    public User get(String username, String password) {
        UserExample example = new UserExample();
        example.createCriteria().andUsernameEqualTo(username).andPasswordEqualTo(password);
        List<User> us = mapper.selectByExample(example);
        return us.isEmpty()?null:us.get(0);
    }
}
