package com.tfkj.dajiang.uav.presenter

import com.architecture.common.base.presenter.BasePresenter
import com.mvp.tfkj.lib.di.ActivityScoped
import com.tfkj.dajiang.uav.contract.DeviceContract
import javax.inject.Inject

/**
 * Created by xuying on 2019-1-2.
 */

@ActivityScoped
class DevicePresenter @Inject constructor() :
        BasePresenter<DeviceContract.View>(),
        DeviceContract.Presenter {
    override fun refresh() {

    }


}