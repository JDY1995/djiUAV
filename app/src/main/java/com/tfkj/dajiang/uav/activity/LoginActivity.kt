package com.tfkj.dajiang.uav.activity

import android.content.Intent
import android.hardware.usb.UsbManager
import android.support.v4.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.architecture.common.base.activity.BaseDaggerFragmentActivity
import com.tfkj.dajiang.uav.arouter.ArouterUrl
import com.tfkj.dajiang.uav.fragment.LoginFragment
import com.tfkj.dajiang.uav.fragment.MainFragment
import dji.sdk.sdkmanager.DJISDKManager
import javax.inject.Inject

/**
 * Created by xuying on 2019-1-2.
 */
@Route(path = ArouterUrl.LoginActivity)
class LoginActivity : BaseDaggerFragmentActivity() {
    @Inject
    lateinit var mFragment: LoginFragment


    override fun initFragment(): Fragment {
        return mFragment
    }

    override fun onNewIntent(intent: Intent) {
        val action = intent.action
        if (UsbManager.ACTION_USB_ACCESSORY_ATTACHED == action) {
            val attachedIntent = Intent()
            attachedIntent.action = DJISDKManager.USB_ACCESSORY_ATTACHED
            sendBroadcast(attachedIntent)
        }
    }
}