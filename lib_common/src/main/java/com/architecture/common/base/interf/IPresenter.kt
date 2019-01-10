package com.architecture.common.base.interf

/**
 * created by xuying at 2018/1/24
 */
interface IPresenter<T> {

    /**
     * Binds presenter with a mView when resumed. The Presenter will perform initialization here.
     *
     * @param view the mView associated with this presenter
     */
    fun takeView(view: T)

    /**
     * 刷新界面的接口
     */
    fun refresh()

    /**
     * Drops the reference to the mView when destroyed
     */
    fun dropView()



}