package com.tfkj.dajiang.uav.activity

import android.support.v4.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.architecture.common.base.activity.BaseDaggerFragmentActivity
import com.tfkj.dajiang.uav.arouter.ArouterUrl
import com.tfkj.dajiang.uav.fragment.AddressFragment
import com.tfkj.dajiang.uav.fragment.DeviceFragment
import javax.inject.Inject

/**
 * 执法地点
 * Created by xuying on 2019-1-2.
 */
@Route(path = ArouterUrl.AddressActivity)
class AddressActivity : BaseDaggerFragmentActivity() {
    @Inject
    lateinit var mFragment: AddressFragment


    override fun initFragment(): Fragment {
        return mFragment
    }

}