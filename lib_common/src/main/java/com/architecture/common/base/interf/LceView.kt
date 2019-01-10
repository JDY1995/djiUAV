package com.architecture.common.base.interf

/**
 * created by xuying at 2018/1/24
 */
interface LceView<M> : IBaseView {

    //显示正在刷新
    fun showWaiting()

    //刷新列表成功
    fun showRefreshList(resultList: MutableList<M>)

    //加载更多失败
    fun showMoreList(resultList: MutableList<M>)

    //添加数据
    fun addData(resultList: MutableList<M>, index: Int)

    //删除
    fun remove(index: Int)

    //更新
    fun setData(data: M, index: Int)

    //获取列表中的数据
    fun getData(): MutableList<M>

    //显示列表失败
    fun showRefreshFail()

    //显示列表失败
    fun showMoreFail()


}