package com.architecture.common.model

/**
 * Created by Administrator on 2018/3/5 0005.
 */
class PageListModel {

    //获取一页中内容限制
    var pageCount: Int = 5
    //下一次开始
    var pageNO: Int = 1

    fun refresh() {
        pageNO = 1
    }

}
