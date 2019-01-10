package com.tfkj.dajiang.uav

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.architecture.common.base.BaseDaggerApplication
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import debug.di.DaggerAppComponent
import dji.sdk.base.BaseProduct
import dji.sdk.products.Aircraft
import dji.sdk.products.HandHeld
import dji.sdk.sdkmanager.BluetoothProductConnector
import dji.sdk.sdkmanager.DJISDKManager

/**
 * Created by xuying on 2018-12-29.
 */
class MainApplication : BaseDaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }

    val TAG = MainApplication::class.java.name


    companion object {
        private var app: Application? = null
        private var product: BaseProduct? = null
        private var bluetoothConnector: BluetoothProductConnector? = null
        /**
         * Gets instance of the specific product connected after the
         * API KEY is successfully validated. Please make sure the
         * API_KEY has been added in the Manifest
         */
        @Synchronized
        fun getProductInstance(): BaseProduct? {
            product = DJISDKManager.getInstance().product
            return product
        }

        @Synchronized
        fun getBluetoothProductConnector(): BluetoothProductConnector? {
            bluetoothConnector = DJISDKManager.getInstance().bluetoothProductConnector
            return bluetoothConnector
        }

        fun isAircraftConnected(): Boolean {
            return getProductInstance() != null && getProductInstance() is Aircraft
        }

        fun isHandHeldConnected(): Boolean {
            return getProductInstance() != null && getProductInstance() is HandHeld
        }

        @Synchronized
        fun getAircraftInstance(): Aircraft? {
            return if (!isAircraftConnected()) {
                null
            } else getProductInstance() as Aircraft?
        }

        @Synchronized
        fun getHandHeldInstance(): HandHeld? {
            return if (!isHandHeldConnected()) {
                null
            } else getProductInstance() as HandHeld?
        }


        @JvmStatic
        fun getInstance(): Application? {
            return app
        }

    }


    override fun attachBaseContext(paramContext: Context) {
        super.attachBaseContext(paramContext)
        MultiDex.install(this)
        com.secneo.sdk.Helper.install(this)
        app = this
    }


}