package com.architecture.common.model.data;

import java.util.List;

/**
 * Created by TangTz on 2018/6/6.
 */

public class BaseResultListDto<T> extends BaseDto {
    public List<T> result;

    public List<T> getData() {
        return result;
    }

    public void setData(List<T> result) {
        this.result = result;
    }
}
