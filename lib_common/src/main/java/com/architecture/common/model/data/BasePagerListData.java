package com.architecture.common.model.data;

import java.util.List;

/**
 * Created by Administrator on 2018/4/7 0007.
 */

public class BasePagerListData<T> {
    int rowSize;
    int pageNO;
    int pageCount;
    private List<T> list;


    public int getPageNO() {
        return pageNO;
    }

    public void setPageNO(int pageNO) {
        this.pageNO = pageNO;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int size) {
        this.pageCount = size;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getRowSize() {
        return rowSize;
    }

    public void setRowSize(int rowSize) {
        this.rowSize = rowSize;
    }

}
