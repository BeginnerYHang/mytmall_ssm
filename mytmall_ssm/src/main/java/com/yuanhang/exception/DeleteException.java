package com.yuanhang.exception;

/**
 * Description:处理级联删除时的异常
 * @author yuanhang
 * @date 2020-06-24 10:49
 */
public class DeleteException extends Exception {

    public DeleteException(){

    }

    public DeleteException(String msg){
        super(msg);
    }
}
