package com.architecture.common.base.fragment

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.architecture.common.R
import com.architecture.common.base.interf.LcePresenter
import com.architecture.common.base.interf.LceView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder


/**
 * 有presenter的列表界面
 * M 数据
 * K BaseViewHolder
 * V View
 * P presenter
 * Created by Administrator on 2018/2/11 0011.
 */
abstract class BaseListPresenterFragment<M, K : BaseViewHolder, V : LceView<M>, P : LcePresenter<V>> :
        BasePresenterFragment<M, V, P>() {

    //列表
    lateinit var mRecyclerView: RecyclerView
    lateinit var mLinearLayoutManager: LinearLayoutManager
    lateinit var mAdapter: BaseQuickAdapter<M, K>
    //是否刷新
    override fun isRefresh(): Boolean {
        return true
    }

    public override fun initView() {
        super.initView()
        //设置配置器
        setAdapter()
        //设置单列的RecyclerView，默认设置，可以自由修改
        setRecyclerView()
        //显示正在加载
        showWaiting()
    }

    override fun findViewById() {
        super.findViewById()
        mRecyclerView = mView.findViewById(R.id.recyclerView)
    }

    /**
     * 重写刷新，实现上拉下拉
     */
    override fun setRefreshLayout() {
        mRefreshLayout?.isEnableLoadMore = false
        mRefreshLayout?.setOnRefreshListener { refreshlayout ->
            (getPresenter() as P).getRefreshList()
//            refreshlayout.finishRefresh(2000/*,false*/)//传入false表示刷新失败
        }
        mRefreshLayout?.setOnLoadMoreListener { refreshlayout ->
            (getPresenter() as P).getMoreList()
//            refreshlayout.finishLoadMore(2000/*,false*/)//传入false表示加载失败
        }
    }


    /**
     * 设置配置器
     */
    protected abstract fun setAdapter()

    /**
     * 设置RecyclerView单列的列表
     */
    open fun setRecyclerView() {
        mLinearLayoutManager = LinearLayoutManager(mActivity)
        if (mRecyclerView != null && mAdapter != null) {
            mRecyclerView.setHasFixedSize(true)
            //设置layoutManager
            mRecyclerView.layoutManager = mLinearLayoutManager
            mRecyclerView.adapter = mAdapter
        }
    }


    /**
     * 刷新列表
     *
     * @param resultList
     */
    fun showRefreshList(resultList: MutableList<M>) {
        mAdapter.setNewData(resultList)
        mRefreshLayout?.finishRefresh()
        //是否显示空内容
        if (resultList == null || resultList.isEmpty()) {
            mAdapter.emptyView = showNullDataView()
        }
    }

    /**
     * 更多数据
     *
     * @param resultList
     */
    fun showMoreList(resultList: MutableList<M>) {
        mAdapter.addData(resultList)
        mRefreshLayout?.finishLoadMore()

    }

    fun getData(): MutableList<M> {
        return mAdapter.data
    }

    /**
     * 添加数据
     */
    fun addData(resultList: MutableList<M>, index: Int = -1) {
        if (index == -1) {
            mAdapter.addData(resultList)
        } else {
            mAdapter.addData(index, resultList)
        }

    }

    /**
     * 删除数据
     */
    fun remove(index: Int) {
        mAdapter.remove(index)
    }

    /**
     * 更新数据
     */
    fun setData(data: M, index: Int) {
        mAdapter.setData(index, data)
    }


    fun showWaiting() {
        mAdapter.emptyView = super.showWaitingView()
    }

    /**
     * 重写刷新列表失败
     */
    override fun showRefreshFail() {
        mRefreshLayout?.finishRefresh(false)
        mAdapter.emptyView = showNullNetworkView()
    }

    //加载更多失败
    fun showMoreFail() {
        mRefreshLayout?.finishLoadMore(false)
    }
}