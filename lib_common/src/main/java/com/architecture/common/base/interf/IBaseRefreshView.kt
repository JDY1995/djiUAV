package com.architecture.common.base.interf

/**
 * 只有下拉刷新的非列表界面
 * Created by xuying on 2018/5/17.
 */
interface IBaseRefreshView<M> : IBaseView {


    //隐藏刷新
    fun hideRefresh()

    //刷新成功
    fun showRefreshSuccess(value: M)

    //刷新失败
    fun showRefreshFail()
}