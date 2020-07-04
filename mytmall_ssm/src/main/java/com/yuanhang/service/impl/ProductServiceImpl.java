package com.yuanhang.service.impl;

import com.yuanhang.mapper.ProductMapper;
import com.yuanhang.pojo.OrderItem;
import com.yuanhang.pojo.Product;
import com.yuanhang.pojo.ProductExample;
import com.yuanhang.pojo.ProductImage;
import com.yuanhang.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yuanhang
 * @Description
 * @date 2020-05-31 17:54
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper mapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductImageService productImageService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private ReviewService reviewService;

    @Override
    public Product findOne(int id) {
        Product p = mapper.selectByPrimaryKey(id);
        setCategory(p);
        setFirstProductImage(p);
        //设置商品的小图、大图、销量和评价数量属性，方便商品详情页面展示
        setSingleImages(p);
        setDoubleImages(p);
        setSaleCount(p);
        setReviewCount(p);
        return p;
    }

    @Override
    public List<Product> findAll(int cid) {
        ProductExample example = new ProductExample();
        example.createCriteria().andCidEqualTo(cid);
        List<Product> ps = mapper.selectByExample(example);
        setFirstProductImage(ps);
        for (Product p : ps) {
            setSingleImages(p);
            setDoubleImages(p);
            setSaleCount(p);
            setReviewCount(p);
        }
        return ps;
    }

    @Override
    public void saveProduct(Product product) {
        mapper.insert(product);
    }

    @Override
    public void deleteProduct(int id) {
        mapper.deleteByPrimaryKey(id);
    }

    @Override
    public void updateProduct(Product product) {
        mapper.updateByPrimaryKeySelective(product);
    }

    @Override
    public List<Product> search(String keyword) {
        ProductExample example = new ProductExample();
        example.createCriteria().andNameLike("%" + keyword + "%");
        List<Product> ps = mapper.selectByExample(example);
        setFirstProductImage(ps);
        return ps;
    }

    private void setCategory(Product product){
        int cid = product.getCid();
        product.setCategory(categoryService.findOne(cid));
    }

    private void setFirstProductImage(Product product){
        int id = product.getId();
        List<ProductImage> pis = productImageService.findAll(id,ProductImageService.TYPE_SINGLE);
        if (!pis.isEmpty()){
            product.setFirstProductImage(pis.get(0));
        }
    }

    private void setFirstProductImage(List<Product> ps){
        if (ps != null && !ps.isEmpty()){
            for (Product p : ps) {
                setFirstProductImage(p);
            }
        }
    }

    private void setSingleImages(Product p){
        p.setProductSingleImages(productImageService.findAll(p.getId(),"type_single"));
    }

    private void setDoubleImages(Product p){
        p.setProductDetailImages(productImageService.findAll(p.getId(),"type_detail"));
    }

    private void setSaleCount(Product p){
        int saleCount = 0;
        List<OrderItem> os = orderItemService.findAllByPid(p.getId());
        for (OrderItem orderItem : os) {
            saleCount += orderItem.getNumber();
        }
        p.setSaleCount(saleCount);
    }

    private void setReviewCount(Product p){
        p.setReviewCount(reviewService.findCount(p.getId()));
    }
}
