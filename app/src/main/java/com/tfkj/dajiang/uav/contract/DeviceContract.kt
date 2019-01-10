package com.tfkj.dajiang.uav.contract

import com.architecture.common.base.interf.IBaseView
import com.architecture.common.base.interf.IPresenter

/**
 * Created by xuying on 2019-1-2.
 */
interface DeviceContract {
    interface View : IBaseView {

    }

    interface Presenter : IPresenter<View> {

    }
}