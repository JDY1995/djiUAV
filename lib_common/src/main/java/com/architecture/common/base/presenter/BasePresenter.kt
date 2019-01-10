package com.architecture.common.base.presenter

import com.architecture.common.base.interf.IBaseView
import com.architecture.common.base.interf.IPresenter
import java.lang.ref.WeakReference

/**
 * created by xuying at 2018/1/24
 */
abstract class BasePresenter<V : IBaseView> : IPresenter<V> {

    //是否刷新过的状态，默认为刷新过
    var isRefrestState = false

    //接口类型的弱引用,防止内存泄漏
    private var viewRef: WeakReference<V>? = null
    protected lateinit var mView: V
    //把View传递给presenter
    override fun takeView(view: V) {
        viewRef = WeakReference(view)
        this.mView = viewRef?.get()!!
    }

    override fun dropView() {
        viewRef?.clear()
    }

}