package com.tfkj.dajiang.uav.fragment

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import com.apkfuns.logutils.LogUtils
import com.architecture.common.base.fragment.BasePresenterFragment
import com.architecture.common.base.interf.IPresenter
import com.mvp.tfkj.lib.di.ActivityScoped
import com.tencent.bugly.crashreport.CrashReport
import com.tfkj.dajiang.uav.MainApplication
import com.tfkj.dajiang.uav.R
import com.tfkj.dajiang.uav.adapter.MainAdapter
import com.tfkj.dajiang.uav.contract.MainContract
import com.tfkj.dajiang.uav.utils.ToastUtils
import dji.common.camera.SettingsDefinitions
import io.reactivex.functions.Consumer
import javax.inject.Inject

/**
 * Created by xuying on 2018-12-29.
 */

@ActivityScoped
class MainFragment @Inject constructor()
    : BasePresenterFragment<Any, MainContract.View, MainContract.Presenter>(),
        MainContract.View {

    @Inject
    lateinit var mPresenter: MainContract.Presenter

    override fun getPresenter(): IPresenter<MainContract.View> {
        return mPresenter
    }

    @Inject
    lateinit var mAdpater: MainAdapter

    lateinit var viewpager: ViewPager

    lateinit var tabs: TabLayout

    override fun initLayoutId(): Int {
        return R.layout.activity_main
    }


    override fun initView() {
        super.initView()
        viewpager = mView.findViewById(R.id.viewpager)
        tabs = mView.findViewById(R.id.tabs)
        setAdapter()
    }

    private fun setAdapter() {
        viewpager.adapter = mAdpater
        tabs.tabMode = TabLayout.MODE_FIXED
        tabs.setupWithViewPager(viewpager)//将TabLayout和ViewPager关联起来。
        TabLayoutHelp.setTab(mActivity, tabs)

    }


}
