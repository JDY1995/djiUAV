package com.tfkj.dajiang.uav.contract

import com.architecture.common.base.interf.IBaseView
import com.architecture.common.base.interf.IPresenter

/**
 * Created by xuying on 2018-12-29.
 */
interface ShowContract {
    interface View : IBaseView {

    }

    interface Presenter : IPresenter<View> {

    }
}