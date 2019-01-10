package com.tfkj.dajiang.uav.presenter

import com.architecture.common.base.presenter.BasePresenter
import com.mvp.tfkj.lib.di.ActivityScoped
import com.tfkj.dajiang.uav.contract.AddressContract
import javax.inject.Inject

/**
 * Created by xuying on 2019-1-2.
 */

@ActivityScoped
class AddressPresenter @Inject constructor() :
        BasePresenter<AddressContract.View>(),
        AddressContract.Presenter {
    override fun refresh() {

    }


}