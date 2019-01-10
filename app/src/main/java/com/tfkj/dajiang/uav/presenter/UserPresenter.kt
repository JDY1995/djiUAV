package com.tfkj.dajiang.uav.presenter

import android.Manifest
import android.content.pm.PackageManager
import android.os.AsyncTask
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.Toast
import com.architecture.common.base.presenter.BasePresenter
import com.mvp.tfkj.lib.di.ActivityScoped
import com.tfkj.dajiang.uav.R
import com.tfkj.dajiang.uav.contract.UserContract
import dji.common.error.DJIError
import dji.common.error.DJISDKError
import dji.log.DJILog
import dji.sdk.base.BaseComponent
import dji.sdk.base.BaseProduct
import dji.sdk.sdkmanager.DJISDKManager
import java.util.ArrayList
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

/**
 * Created by xuying on 2018-12-29.
 */
@ActivityScoped
class UserPresenter @Inject constructor() :
        BasePresenter<UserContract.View>(),
        UserContract.Presenter {
    private val TAG = UserPresenter::class.java!!.getSimpleName()


    override fun refresh() {

    }

    override fun takeView(view: UserContract.View) {
        super.takeView(view)

    }


}