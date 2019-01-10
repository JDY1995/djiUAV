package com.architecture.common.base.interf

import com.trello.rxlifecycle2.components.support.RxFragment


/**
 * 基本界面
 * created by xuying at 2018/1/24
 */
 interface IBaseView : IView {

    fun isActive(): Boolean

    //显示等待对话框
    fun showWaitDialog(content: String)

    //隐藏对话框
    fun hideDialog()

    fun showError(value: String)

    fun showSuccess(value: String)

    fun showInfo(value: String)

    fun finishActivity()


}