package com.example.administrator.recyclerviewsuspension.base;

/**
 * Created by JiaPing on 2017/12/27.
 * 数据返回体的基本接受类
 */

public class BaseResponse<T> {
    private T data;

    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
