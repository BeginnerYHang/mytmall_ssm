package com.yuanhang.pojo;

import com.yuanhang.service.OrderService;

import java.util.Date;
import java.util.List;

public class Order {
    private Integer id;

    private String orderCode;

    private String address;

    private String post;

    private String receiver;

    private String mobile;

    private String userMessage;

    private Date createDate;

    private Date payDate;

    private Date deliveryDate;

    private Date confirmDate;

    private Integer uid;

    private String status;

    /**
     * 以下为非数据库表字段
     */
    private List<OrderItem> orderItems;


    private User user;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode == null ? null : orderCode.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post == null ? null : post.trim();
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver == null ? null : receiver.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage == null ? null : userMessage.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Date getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getMoney(){
        double money = 0.0;
        for (OrderItem orderItem : orderItems) {
            money += orderItem.getNumber() * orderItem.getProduct().getPromotePrice();
        }
        return money;
    }

    public int getTotalNum(){
        int totalNum = 0;
        for (OrderItem orderItem : orderItems) {
            totalNum += orderItem.getNumber();
        }
        return totalNum;
    }

    //将状态描述转为中文
    public String getStatusDesc(){
        String defStatus ="未知";
        switch (status){
            case OrderService.WAIT_PAY:
                defStatus = "待付款";
                break;
            case OrderService.WAIT_DELIVERY :
                defStatus =  "待发货";
                break;
            case OrderService.WAIT_CONFIRM:
                defStatus = "待收货";
                break;
            case OrderService.WAIT_REVIEW:
                defStatus = "待评价";
                break;
            case OrderService.FINISH:
                defStatus = "完成";
                break;
            case OrderService.DELETE:
                defStatus = "删除";
                break;
            default:
        }
        return defStatus;
    }
}