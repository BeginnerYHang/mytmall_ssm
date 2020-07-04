package com.yuanhang.util.comparator;

import com.yuanhang.pojo.Product;

import java.util.Comparator;

/**
 * Description:
 *
 * @author yuanhang
 * @date 2020-06-12 14:32
 */
public class ProductSaleComparator implements Comparator<Product> {
    @Override
    public int compare(Product o1, Product o2) {
        return o2.getSaleCount() - o1.getSaleCount();
    }
}
