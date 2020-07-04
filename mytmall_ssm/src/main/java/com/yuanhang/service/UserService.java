package com.yuanhang.service;

import com.yuanhang.pojo.User;

import java.util.List;

/**
 * @author yuanhang
 * @Description
 * @date 2020-06-03 20:44
 */
public interface UserService {
    List<User> findAll();

    User findOne(int id);

    boolean isExist(String username);

    void add(User user);

    User get(String username,String password);
}
