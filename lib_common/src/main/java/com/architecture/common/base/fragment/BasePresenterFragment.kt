package com.architecture.common.base.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.architecture.common.R
import com.architecture.common.base.interf.IBaseRefreshView
import com.architecture.common.base.interf.IPresenter
import com.architecture.common.base.interf.IView
import com.jakewharton.rxbinding2.view.RxView
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import java.util.concurrent.TimeUnit


/**
 * @param V 这个组中的成员的类型。继承
 * @see IView
 * @param P 这个组中的成员的类型。继承
 * @see IPresenter
 * @constructor 创建一个包含presenter的fragment
 * created by xuying at 2018/1/25
 */
abstract class BasePresenterFragment<M, V : IView, P : IPresenter<V>>
    : BaseDaggerFragment<V, P>(),
        IBaseRefreshView<M> {

    //默认为null
    var mRefreshLayout: SmartRefreshLayout? = null
    //默认为null,顶层的VIEW
    var mLayoutNull: ViewGroup? = null

    /**
     * 重写此方法，来说明是否刷新，默认不刷新
     * 如果需要刷新，请加上mRefreshLayout和mLayoutNull
     */
    open fun isRefresh(): Boolean {
        return false
    }

    //懒加载刷新数据
    override fun lazyLoad() {
        if (isLazyLoad) {
            getPresenter().takeView(this as V)
            (getPresenter() as P).refresh()
        }
    }

    override fun onResume() {
        super.onResume()
        if (!isLazyLoad) {
            getPresenter().takeView(this as V)
            (getPresenter() as P).refresh()
        }
    }

    abstract fun getPresenter(): IPresenter<V>

    override fun initView() {
        findViewById()
        //设置加载更多
        setRefreshLayout()
    }

    open fun findViewById() {
        if (isRefresh()) {
            mRefreshLayout = mView.findViewById(R.id.refreshLayout) as SmartRefreshLayout
            mLayoutNull = mView.findViewById(R.id.layoutNull) as ViewGroup
        }
    }

    /**
     * 默认关闭上拉下拉
     */
    open fun setRefreshLayout() {

        mRefreshLayout?.isEnableRefresh = isRefresh()
        mRefreshLayout?.isEnableLoadMore = false
        mRefreshLayout?.setOnRefreshListener { refreshlayout ->
            (getPresenter() as P).refresh()
        }

    }

    //隐藏刷新
    override fun hideRefresh() {
        mRefreshLayout?.finishRefresh()
    }

    //刷新成功
    override fun showRefreshSuccess(value: M) {
        mLayoutNull?.removeAllViews()
        mRefreshLayout?.finishRefresh(true)
    }

    //刷新列表失败
    override fun showRefreshFail() {
        mLayoutNull?.removeAllViews()
        mLayoutNull?.addView(showNullNetworkView())
        mRefreshLayout?.finishRefresh(false)
    }

    /**
     * 等待，没有网络，空数据的默认界面
     */
    fun showWaitingView(): View {
        return LayoutInflater.from(mActivity).inflate(R.layout.null_wait, null)
    }

    fun showNullNetworkView(): View {
        val view = LayoutInflater.from(mActivity).inflate(R.layout.null_network, null)
        val btnRefresh = view.findViewById<View>(R.id.btnRefresh) as Button
        //重新刷新数据
        RxView.clicks(btnRefresh).throttleFirst(300, TimeUnit.MILLISECONDS).subscribe { aVoid ->
            (getPresenter() as P).refresh()
        }
        return view
    }

    fun showNullDataView(): View {
        return LayoutInflater.from(mActivity).inflate(R.layout.null_data, null)
    }

    fun showNullData() {
        mLayoutNull?.removeAllViews()
        mLayoutNull?.addView(showNullDataView())
    }

    override fun onDestroy() {
        super.onDestroy()
        getPresenter().dropView()

    }
}