package com.yuanhang.pojo;

import java.util.List;

/**
 * @author yuanhang
 * @Description :自定义泛型类PageBean用于分页
 * @date 2020-05-29 9:17

public class PageBean<T> {
    //数据总条数
    private int totalCount;
    //数据总页数
    private int totalPage;
    //当前页
    private int currentPage;
    //当前页所放数据条数
    private int pageSize;
    //每页数据
    private List<T> pageData;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getPageData() {
        return pageData;
    }

    public void setPageData(List<T> pageData) {
        this.pageData = pageData;
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "totalCount=" + totalCount +
                ", totalPage=" + totalPage +
                ", currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", pageData=" + pageData +
                '}';
    }
}*/
