package com.tfkj.dajiang.uav.fragment

import android.service.autofill.TextValueSanitizer
import android.widget.TextView
import com.architecture.common.base.fragment.BasePresenterFragment
import com.architecture.common.base.interf.IPresenter
import com.jakewharton.rxbinding2.view.RxView
import com.mvp.tfkj.lib.di.ActivityScoped
import com.tfkj.dajiang.uav.R
import com.tfkj.dajiang.uav.contract.AddressContract
import javax.inject.Inject

/**
 * Created by xuying on 2019-1-2.
 */
@ActivityScoped
class AddressFragment @Inject constructor()
    : BasePresenterFragment<Any, AddressContract.View, AddressContract.Presenter>(),
        AddressContract.View {

    lateinit var tvAddress: TextView

    @Inject
    lateinit var mPresenter: AddressContract.Presenter

    override fun getPresenter(): IPresenter<AddressContract.View> {
        return mPresenter
    }


    override fun initLayoutId(): Int {
        return R.layout.activity_address
    }


}
