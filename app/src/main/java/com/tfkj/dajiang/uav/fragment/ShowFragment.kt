package com.tfkj.dajiang.uav.fragment

import android.os.Bundle
import com.apkfuns.logutils.LogUtils
import com.architecture.common.base.fragment.BasePresenterFragment
import com.architecture.common.base.interf.IPresenter
import com.mvp.tfkj.lib.di.ActivityScoped
import com.tfkj.dajiang.uav.MainApplication
import com.tfkj.dajiang.uav.R
import com.tfkj.dajiang.uav.contract.ShowContract
import com.tfkj.dajiang.uav.utils.ToastUtils
import dji.common.camera.SettingsDefinitions
import javax.inject.Inject

/**
 * 显示界面
 * Created by xuying on 2018-12-29.
 */

@ActivityScoped
class ShowFragment @Inject constructor()
    : BasePresenterFragment<Any, ShowContract.View, ShowContract.Presenter>(),
        ShowContract.View {

    @Inject
    lateinit var mPresenter: ShowContract.Presenter

    override fun getPresenter(): IPresenter<ShowContract.View> {
        return mPresenter
    }

    override fun initLayoutId(): Int {
        return R.layout.activity_show
    }


    override fun initView() {
        super.initView()

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (isModuleAvailable()) {
            ToastUtils.showToast("可以使用")
            MainApplication.getProductInstance()
                    ?.camera
                    ?.setMode(SettingsDefinitions.CameraMode.SHOOT_PHOTO
                    ) {
                        LogUtils.e(it)
                    }
        }
    }

    private fun isModuleAvailable(): Boolean {
        return null != MainApplication.getProductInstance() &&
                null != MainApplication.getProductInstance()
                ?.camera
    }


}
