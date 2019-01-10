package com.tfkj.dajiang.uav.fragment

import android.annotation.SuppressLint
import android.service.autofill.TextValueSanitizer
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.alibaba.android.arouter.launcher.ARouter
import com.architecture.common.base.fragment.BasePresenterFragment
import com.architecture.common.base.interf.IPresenter
import com.jakewharton.rxbinding2.view.RxView
import com.mvp.tfkj.lib.di.ActivityScoped
import com.tfkj.dajiang.uav.R
import com.tfkj.dajiang.uav.arouter.ArouterNavigate
import com.tfkj.dajiang.uav.contract.LoginContract
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by xuying on 2019-1-2.
 */
@ActivityScoped
class LoginFragment @Inject constructor()
    : BasePresenterFragment<Any, LoginContract.View, LoginContract.Presenter>(),
        LoginContract.View {

    lateinit var tvLogin: TextView
    lateinit var btnLogin: Button

    @Inject
    lateinit var mPresenter: LoginContract.Presenter

    override fun getPresenter(): IPresenter<LoginContract.View> {
        return mPresenter
    }


    override fun initLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun initView() {
        super.initView()
        mActivity.hideTitle()
        setListener()
    }

    override fun findViewById() {
        super.findViewById()
        btnLogin = mView.findViewById(R.id.btnLogin)
        tvLogin = mView.findViewById(R.id.tvLogin)
    }


    @SuppressLint("CheckResult")
    private fun setListener() {
        RxView.clicks(btnLogin)
                .throttleFirst(200, TimeUnit.MILLISECONDS)
                .subscribe {

                }
        RxView.clicks(tvLogin)
                .throttleFirst(200, TimeUnit.MILLISECONDS)
                .subscribe {
                    ArouterNavigate.deviceActivity()
                    mActivity.finish()
                }
    }
}
