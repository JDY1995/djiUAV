package com.tfkj.dajiang.uav.activity

import android.support.v4.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.architecture.common.base.activity.BaseDaggerFragmentActivity
import com.tfkj.dajiang.uav.arouter.ArouterUrl
import com.tfkj.dajiang.uav.fragment.DeviceFragment
import com.tfkj.dajiang.uav.fragment.LoginFragment
import javax.inject.Inject

/**
 * 设备信息页面
 * Created by xuying on 2019-1-2.
 */
@Route(path = ArouterUrl.DeviceActivity)
class DeviceActivity : BaseDaggerFragmentActivity() {
    @Inject
    lateinit var mFragment: DeviceFragment


    override fun initFragment(): Fragment {
        return mFragment
    }

}