package com.yuanhang.util;

/**
 * Description:请求消息传递类
 *
 * @author yuanhang
 * @date 2020-06-21 11:59
 */
public class Message {
    private boolean flag;
    private Object data;

    public Message(boolean flag) {
        this.flag = flag;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
