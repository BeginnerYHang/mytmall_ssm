package com.yuanhang.util;

/**
 * @author yuanhang
 * @Description :Page用于分页显示
 * @date 2020-05-30 8:38
 */
public class Page {
    //每页的开始下标(数据库中)
    private int start;
    //每页显示个数
    private int count;
    //总个数
    private int total;
    //参数
    private String param;
    //默认每页显示5条数据
    private static final int DEFAULT_COUNT = 5;

    public Page(){
        count = DEFAULT_COUNT;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    //得到最后一页的开始下标
    public int getLast(){
        if (total % count == 0){
            return total - count;
        }
        return (total/count)*count;
    }

    //得到总页数
    public int getTotalPage(){
        return total%count == 0 ? total/count : total/count + 1;
    }

    //加上判断是否有前一页,是否有后一页的代码逻辑
    public boolean isHasPrevious(){
        return !(start == 0);
    }

    public boolean isHasNext(){
        return !(start == getLast());
    }

    @Override
    public String toString() {
        return "Page{" +
                "start=" + start +
                ", count=" + count +
                ", total=" + total +
                ", param='" + param + '\'' +
                '}';
    }
}
