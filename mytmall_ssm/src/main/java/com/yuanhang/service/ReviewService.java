package com.yuanhang.service;

import com.yuanhang.pojo.Product;
import com.yuanhang.pojo.Review;

import java.util.List;

/**
 * Description:
 *
 * @author yuanhang
 * @date 2020-06-11 10:05
 */
public interface ReviewService {
    List<Review> findAll(int pid);

    int findCount(int pid);

    void fill(Product product);

    void add(Review review);

    /**
     * Description :去数据库查询该用户是否评价了该商品
     * @param pid
     * @param uid
     * @return boolean
     * @author yuanhang
     * @date 2020/6/19 21:54
     */
    boolean isReview(int pid,int uid);
}
