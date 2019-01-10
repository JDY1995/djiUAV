package com.architecture.common.base.presenter

import com.architecture.common.base.interf.LcePresenter
import com.architecture.common.base.interf.LceView
import javax.inject.Inject

/**
 * Created by xuying on 2018-7-11.
 */
class ListPresenter @Inject constructor()
    : BaseListPresenter<Any, LceView<Any>>(),
        LcePresenter<LceView<Any>> {
    override fun getMoreList() {

    }

}
