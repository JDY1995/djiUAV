package com.architecture.common.model.data;

/**
 * Created by TangTz on 2018/6/6.
 */

public class BaseTaskListDto<T> extends BaseDto {
    public T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
