package com.architecture.common.base.fragment

import com.architecture.common.base.interf.IPresenter
import com.architecture.common.base.interf.LcePresenter
import com.architecture.common.base.interf.LceView
import com.architecture.common.base.presenter.ListPresenter
import com.chad.library.adapter.base.BaseViewHolder
import javax.inject.Inject

/**
 * 设计一个无用的注入，防止在多组件情况下，出现重复注入的问题
 * 解决XX_MembersInjector.class is repeatedly generated in the wrong package
 * Created by xuying on 2018-7-11.
 */
class ListPresenterFragment @Inject constructor() :
        BaseListPresenterFragment<Any, BaseViewHolder, LceView<Any>, LcePresenter<LceView<Any>>>(),
        LceView<Any> {

    override fun getPresenter(): IPresenter<LceView<Any>> {
        return ListPresenter()
    }

    override fun setAdapter() {
    }

    override fun initLayoutId(): Int {
        return 1
    }

}
