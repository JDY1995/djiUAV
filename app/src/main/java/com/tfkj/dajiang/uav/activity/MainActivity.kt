package com.tfkj.dajiang.uav.activity

import android.content.Intent
import android.hardware.usb.UsbManager
import android.os.Handler
import android.os.Looper
import android.support.v4.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.architecture.common.base.activity.BaseDaggerFragmentActivity
import com.tfkj.dajiang.uav.arouter.ArouterUrl
import com.tfkj.dajiang.uav.fragment.MainFragment
import dji.sdk.sdkmanager.DJISDKManager
import javax.inject.Inject

@Route(path = ArouterUrl.MainActivity)
class MainActivity : BaseDaggerFragmentActivity() {
    @Inject
    lateinit var mFragment: MainFragment


    override fun initFragment(): Fragment {
        return mFragment
    }



}
