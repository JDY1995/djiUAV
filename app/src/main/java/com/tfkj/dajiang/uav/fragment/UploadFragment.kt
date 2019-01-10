package com.tfkj.dajiang.uav.fragment

import android.os.Bundle
import com.apkfuns.logutils.LogUtils
import com.architecture.common.base.fragment.BasePresenterFragment
import com.architecture.common.base.interf.IPresenter
import com.mvp.tfkj.lib.di.ActivityScoped
import com.tfkj.dajiang.uav.MainApplication
import com.tfkj.dajiang.uav.R
import com.tfkj.dajiang.uav.contract.UploadContract
import com.tfkj.dajiang.uav.utils.ToastUtils
import dji.common.camera.SettingsDefinitions
import javax.inject.Inject

/**
 * 显示界面
 * Created by xuying on 2018-12-29.
 */

@ActivityScoped
class UploadFragment @Inject constructor()
    : BasePresenterFragment<Any, UploadContract.View, UploadContract.Presenter>(),
        UploadContract.View {

    @Inject
    lateinit var mPresenter: UploadContract.Presenter

    override fun getPresenter(): IPresenter<UploadContract.View> {
        return mPresenter
    }

    override fun initLayoutId(): Int {
        return R.layout.activity_address
    }


    override fun initView() {
        super.initView()

    }





}
