package com.yuanhang.util.comparator;

import com.yuanhang.pojo.Product;

import java.util.Comparator;

/**
 * Description:按照销量和销售额的乘积进行排序(sort="all"时的比较器)
 * compare方法大于0，就把前一个数和后一个数交换，如果小于等于0就保持原位置，不进行交换
 * 泛型在继承时的问题
 * @author yuanhang
 * @date 2020-06-12 13:02
 */
public class ProductAllComparator implements Comparator<Product>{

    @Override
    public int compare(Product o1, Product o2) {
        return o2.getSaleCount() * o2.getReviewCount() - o1.getSaleCount() * o1.getReviewCount();
    }
}

