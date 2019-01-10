package com.architecture.common.model.data;

import java.util.List;

/**
 * 分页列表数据
 * "data": {
 * "pageNO": 0,
 * "size": 0,
 * "list": [
 * {
 * "id": 0,
 * "userId": 0,
 * …
 * }
 * ]
 * }
 * <p>
 * Created by Administrator on 2016/9/23.
 */
public class BasePageListDto<T> extends BaseDto {

    private int rowSize;
    private List<T> data;

    public int getRowSize() {
        return rowSize;
    }

    public void setRowSize(int rowSize) {
        this.rowSize = rowSize;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
