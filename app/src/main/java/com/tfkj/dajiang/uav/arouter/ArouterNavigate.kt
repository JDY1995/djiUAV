package com.tfkj.dajiang.uav.arouter

import com.alibaba.android.arouter.launcher.ARouter

/**
 * Created by xuying on 2019-1-2.
 */
object ArouterNavigate {
    fun loginActivity() {
        ARouter.getInstance().build(ArouterUrl.LoginActivity).navigation()
    }

    fun mainActivity() {
        ARouter.getInstance().build(ArouterUrl.MainActivity).navigation()
    }

    fun deviceActivity() {
        ARouter.getInstance().build(ArouterUrl.DeviceActivity).navigation()
    }
}