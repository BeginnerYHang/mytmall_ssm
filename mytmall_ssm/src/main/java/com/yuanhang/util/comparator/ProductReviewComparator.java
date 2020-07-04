package com.yuanhang.util.comparator;

import com.yuanhang.pojo.Product;

import java.util.Comparator;

/**
 * Description:
 *
 * @author yuanhang
 * @date 2020-06-12 14:24
 */
public class ProductReviewComparator implements Comparator<Product> {
    @Override
    public int compare(Product o1, Product o2) {
        return o2.getReviewCount() - o1.getReviewCount();
    }
}
