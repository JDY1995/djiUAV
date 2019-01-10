package com.tfkj.dajiang.uav.presenter

import com.architecture.common.base.presenter.BasePresenter
import com.mvp.tfkj.lib.di.ActivityScoped
import com.tfkj.dajiang.uav.contract.LoginContract
import javax.inject.Inject

/**
 * Created by xuying on 2019-1-2.
 */

@ActivityScoped
class LoginPresenter @Inject constructor() :
        BasePresenter<LoginContract.View>(),
        LoginContract.Presenter {
    override fun refresh() {

    }


}