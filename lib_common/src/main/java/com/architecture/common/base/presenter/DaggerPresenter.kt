package com.architecture.common.base.presenter

import com.architecture.common.base.interf.IBaseView
import com.architecture.common.base.interf.IPresenter
import com.architecture.common.base.interf.IView

/**
 * Created by xuying on 2018-9-18.
 */
class DaggerPresenter : IPresenter<IView> {
    override fun takeView(view: IView) {

    }

    override fun refresh() {
    }

    override fun dropView() {
    }

}