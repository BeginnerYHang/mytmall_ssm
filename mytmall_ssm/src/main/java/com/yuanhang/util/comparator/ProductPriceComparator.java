package com.yuanhang.util.comparator;

import com.yuanhang.pojo.Product;

import java.util.Comparator;

/**
 * Description:
 *
 * @author yuanhang
 * @date 2020-06-12 14:33
 */
public class ProductPriceComparator implements Comparator<Product> {

    @Override
    public int compare(Product o1, Product o2) {
        return (int)(o2.getPromotePrice() - o1.getPromotePrice());
    }
}
