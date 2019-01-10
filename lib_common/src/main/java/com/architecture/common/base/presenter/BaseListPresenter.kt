package com.architecture.common.base.presenter

import com.architecture.common.base.interf.LcePresenter
import com.architecture.common.base.interf.LceView
import com.architecture.common.model.PageListModel

/**
 * 基本列表的presenter
 * M数据类型
 * V视图类型
 * Created by xuying on 2018/2/12.
 */
abstract class BaseListPresenter<M, V : LceView<M>> : BasePresenter<V>(), LcePresenter<V> {

    //翻页存储
    var mPageListModel = PageListModel()

    /**
     * 刷新的时候，获取列表数据
     */
    override fun refresh() {
        getRefreshList()

    }

    override fun getRefreshList() {
        mPageListModel.refresh()
    }
}