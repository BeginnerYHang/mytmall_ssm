package com.yuanhang.service.impl;

import com.yuanhang.mapper.ReviewMapper;
import com.yuanhang.pojo.Product;
import com.yuanhang.pojo.Review;
import com.yuanhang.pojo.ReviewExample;
import com.yuanhang.service.ReviewService;
import com.yuanhang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description:
 *
 * @author yuanhang
 * @date 2020-06-11 10:05
 */
@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewMapper mapper;

    @Autowired
    private UserService userService;


    @Override
    public List<Review> findAll(int pid) {
        ReviewExample example = new ReviewExample();
        example.createCriteria().andPidEqualTo(pid);
        List<Review> reviews = mapper.selectByExample(example);
        for (Review review : reviews) {
            review.setUser(userService.findOne(review.getUid()));
        }
        return reviews;
    }

    @Override
    public int findCount(int pid) {
        return findAll(pid).size();
    }

    @Override
    public void fill(Product product) {
        product.setReviews(findAll(product.getId()));
    }

    @Override
    public void add(Review review) {
        mapper.insert(review);
    }

    @Override
    public boolean isReview(int pid, int uid) {
        ReviewExample example = new ReviewExample();
        example.createCriteria().andPidEqualTo(pid).andUidEqualTo(uid);
        List<Review> reviews = mapper.selectByExample(example);
        return !reviews.isEmpty();
    }
}
