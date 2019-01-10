package com.tfkj.dajiang.uav.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.*
import android.service.autofill.TextValueSanitizer
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.apkfuns.logutils.LogUtils
import com.architecture.common.base.fragment.BasePresenterFragment
import com.architecture.common.base.interf.IPresenter
import com.jakewharton.rxbinding2.view.RxView
import com.mvp.tfkj.lib.di.ActivityScoped
import com.tfkj.dajiang.uav.MainApplication
import com.tfkj.dajiang.uav.R
import com.tfkj.dajiang.uav.arouter.ArouterNavigate
import com.tfkj.dajiang.uav.contract.DeviceContract
import com.tfkj.dajiang.uav.utils.ToastUtils
import dji.common.error.DJIError
import dji.common.error.DJISDKError
import dji.common.realname.AppActivationState
import dji.common.useraccount.UserAccountState
import dji.common.util.CommonCallbacks
import dji.keysdk.DJIKey
import dji.keysdk.KeyManager
import dji.keysdk.ProductKey
import dji.keysdk.callback.KeyListener
import dji.log.DJILog
import dji.sdk.base.BaseComponent
import dji.sdk.base.BaseProduct
import dji.sdk.products.Aircraft
import dji.sdk.realname.AppActivationManager
import dji.sdk.sdkmanager.DJISDKManager
import dji.sdk.useraccount.UserAccountManager
import java.util.ArrayList
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

/**
 * 设备界面
 * Created by xuying on 2019-1-2.
 */
@ActivityScoped
class DeviceFragment @Inject constructor()
    : BasePresenterFragment<Any, DeviceContract.View, DeviceContract.Presenter>(),
        DeviceContract.View {
    private val TAG = MainFragment::class.java!!.getSimpleName()
    //权限相关
    private val REQUIRED_PERMISSION_LIST = arrayOf(
            Manifest.permission.VIBRATE,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.WAKE_LOCK,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.RECORD_AUDIO)
    private val REQUEST_PERMISSION_CODE = 12345
    private val missingPermission = ArrayList<String>()
    private val isRegistrationInProgress = AtomicBoolean(false)
    private var hasStartedFirmVersionListener = false
    private val hasAppActivationListenerStarted = AtomicBoolean(false)
    private val MSG_UPDATE_BLUETOOTH_CONNECTOR = 0
    private val MSG_INFORM_ACTIVATION = 1
    private val ACTIVATION_DALAY_TIME = 1000
    private var mHandler: Handler? = null
    private var mHandlerUI: Handler? = null
    private val mHandlerThread = HandlerThread("Bluetooth")
    private var firmwareVersionUpdater: KeyListener? = null
    private var appActivationStateListener: AppActivationState.AppActivationStateListener? = null
    private var firmwareKey: DJIKey? = null
    private var mProduct: BaseProduct? = null
    @Inject
    lateinit var mPresenter: DeviceContract.Presenter

    override fun getPresenter(): IPresenter<DeviceContract.View> {
        return mPresenter
    }

    private lateinit var tvSN: TextView
    private lateinit var tvID: TextView
    private lateinit var tvTime: TextView
    private lateinit var tvEnable: TextView

    private lateinit var btnConnect: Button


    override fun initLayoutId(): Int {
        return R.layout.activity_device
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkAndRequestPermissions()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
        mHandlerThread.start()
        mHandler = object : Handler(mHandlerThread.looper) {
            override fun handleMessage(msg: Message) {
                when (msg.what) {
                    MSG_INFORM_ACTIVATION -> loginToActivationIfNeeded()
                }
            }
        }
        mHandlerUI = Handler(Looper.getMainLooper())
    }

    override fun findViewById() {
        super.findViewById()
        tvSN = mView.findViewById(R.id.tvSN)
        tvID = mView.findViewById(R.id.tvID)
        tvTime = mView.findViewById(R.id.tvTime)
        tvEnable = mView.findViewById(R.id.tvEnable)

        btnConnect = mView.findViewById(R.id.btnConnect)
    }

    override fun initView() {
        super.initView()
        setListener()
    }

    private fun setListener() {
        RxView.clicks(btnConnect)
                .throttleFirst(200, TimeUnit.MILLISECONDS)
                .subscribe {
                    ArouterNavigate.mainActivity()
                    mActivity.finish()
                }
    }

    /**
     * Checks if there is any missing permissions, and
     * requests runtime permission if needed.
     */
    private fun checkAndRequestPermissions() {
        // Check for permissions
        for (eachPermission in REQUIRED_PERMISSION_LIST) {
            if (ContextCompat.checkSelfPermission(mActivity, eachPermission) != PackageManager.PERMISSION_GRANTED) {
                missingPermission.add(eachPermission)
            }
        }
        // Request for missing permissions
        if (missingPermission.isEmpty()) {

            startSDKRegistration()
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(mActivity,
                    missingPermission.toTypedArray(),
                    REQUEST_PERMISSION_CODE)
        }

    }


    private fun startSDKRegistration() {
        if (isRegistrationInProgress.compareAndSet(false, true)) {
            AsyncTask.execute {
                ToastUtils.setResultToToast(getString(R.string.sdk_registration_doing_message))
                DJISDKManager.getInstance().registerApp(mActivity.applicationContext, object : DJISDKManager.SDKManagerCallback {
                    override fun onRegister(djiError: DJIError) {
                        if (djiError === DJISDKError.REGISTRATION_SUCCESS) {
                            DJILog.e("App registration", DJISDKError.REGISTRATION_SUCCESS.description)
                            DJISDKManager.getInstance().startConnectionToProduct()
                            ToastUtils.setResultToToast(mActivity.getString(R.string.sdk_registration_success_message))

                            notifyStatusChange()
                        } else {
                            ToastUtils.setResultToToast(mActivity.getString(R.string.sdk_registration_message) + djiError.description)
                        }
                        LogUtils.e(djiError.description)
                    }

                    override fun onProductDisconnect() {
                        Log.d(TAG, "onProductDisconnect")
                        notifyStatusChange()
                    }

                    override fun onProductConnect(baseProduct: BaseProduct) {
                        Log.d(TAG, String.format("onProductConnect newProduct:%s", baseProduct))
                        notifyStatusChange()
                    }

                    override fun onComponentChange(componentKey: BaseProduct.ComponentKey?,
                                                   oldComponent: BaseComponent?,
                                                   newComponent: BaseComponent?) {
                        newComponent?.setComponentListener(mDJIComponentListener)
                        Log.d(TAG,
                                String.format("onComponentChange key:%s, oldComponent:%s, newComponent:%s",
                                        componentKey ?: "",
                                        oldComponent ?: "",
                                        newComponent ?: ""))

                        notifyStatusChange()
                    }
                })
            }
        }
    }

    private val mDJIComponentListener = BaseComponent.ComponentListener { isConnected ->
        Log.d(TAG, "onComponentConnectivityChanged: $isConnected")
        notifyStatusChange()
    }

    /**
     * Result of runtime permission request
     */
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // Check for granted permission and remove from missing list
        if (requestCode == REQUEST_PERMISSION_CODE) {
            for (i in grantResults.indices.reversed()) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    missingPermission.remove(permissions[i])
                }
            }
        }
        // If there is enough permission, we will start the registration
        if (missingPermission.isEmpty()) {
            startSDKRegistration()
        } else {
            Toast.makeText(mActivity, "Missing permissions!!!", Toast.LENGTH_LONG).show()
        }
    }

    private fun notifyStatusChange() {
        LogUtils.e("mHandlerUI")
        if (mHandlerUI != null) {
            LogUtils.e("mHandlerUI不为空")
            mHandlerUI?.post { refreshDevice() }
        } else {
            LogUtils.e("mHandlerUI为空")
        }

    }

    /**
     * 刷新界面内容
     */
    @SuppressLint("SetTextI18n")
    private fun refreshDevice() {
        var product = MainApplication.getProductInstance()
        mProduct = product
        if (null != product) {
            if (product.isConnected) {
                val str = if (product is Aircraft) "DJIAircraft" else "DJIHandHeld"
                tvEnable.text = "Status: $str connected"
                tryUpdateFirmwareVersionWithListener()
                if (product is Aircraft) {
                    addAppActivationListenerIfNeeded()
                }

                if (null != product.model) {
                    tvID.text = "" + product.model.displayName
                } else {
                    tvID.setText(R.string.product_information)
                }
            } else if (product is Aircraft) {
                val aircraft = product as Aircraft
                if (aircraft.remoteController != null && aircraft.remoteController.isConnected) {
                    tvEnable.setText(R.string.connection_only_rc)
                    tvID.setText(R.string.product_information)
//                    mTextModelAvailable.setText("Firmware version:N/A")
                }
            }
        } else {
            //            mBtnOpen.setEnabled(false);
            tvEnable.setText(R.string.product_information)
            tvID.setText(R.string.connection_loose)
//            mTextModelAvailable.setText("Firmware version:N/A")
        }

    }

    private fun addAppActivationListenerIfNeeded() {
        if (AppActivationManager.getInstance().appActivationState != AppActivationState.ACTIVATED) {
            sendDelayMsg(MSG_INFORM_ACTIVATION, ACTIVATION_DALAY_TIME.toLong())
            if (hasAppActivationListenerStarted.compareAndSet(false, true)) {
                appActivationStateListener = AppActivationState.AppActivationStateListener { appActivationState ->
                    if (mHandler != null && mHandler!!.hasMessages(MSG_INFORM_ACTIVATION)) {
                        mHandler!!.removeMessages(MSG_INFORM_ACTIVATION)
                    }
                    if (appActivationState != AppActivationState.ACTIVATED) {
                        sendDelayMsg(MSG_INFORM_ACTIVATION, ACTIVATION_DALAY_TIME.toLong())
                    }
                }
                AppActivationManager.getInstance().addAppActivationStateListener(appActivationStateListener)
            }
        }
    }

    private fun tryUpdateFirmwareVersionWithListener() {
        if (!hasStartedFirmVersionListener) {
            firmwareVersionUpdater = KeyListener { o, o1 -> mHandlerUI?.post(Runnable { updateVersion() }) }
            firmwareKey = ProductKey.create(ProductKey.FIRMWARE_PACKAGE_VERSION)
            if (KeyManager.getInstance() != null) {
                KeyManager.getInstance().addListener(firmwareKey, firmwareVersionUpdater)
            }
            hasStartedFirmVersionListener = true
        }
        updateVersion()
    }

    private fun sendDelayMsg(msg: Int, delayMillis: Long) {
        if (mHandler != null) {
            mHandler?.sendEmptyMessageDelayed(msg, delayMillis)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateVersion() {
        var version: String? = null
        if (mProduct != null) {
            version = mProduct?.firmwarePackageVersion
        }

        if (TextUtils.isEmpty(version)) {
            tvTime.text = "Firmware version:N/A" //Firmware version:
        } else {
            tvTime.text = "Firmware version:" + version!! //"Firmware version: " +
            removeFirmwareVersionListener()
        }
    }

    private fun removeFirmwareVersionListener() {
        if (hasStartedFirmVersionListener) {
            if (KeyManager.getInstance() != null) {
                KeyManager.getInstance().removeListener(firmwareVersionUpdater)
            }
        }
        hasStartedFirmVersionListener = false
    }

    private fun loginToActivationIfNeeded() {
        if (AppActivationManager.getInstance().appActivationState == AppActivationState.LOGIN_REQUIRED) {
            UserAccountManager.getInstance()
                    .logIntoDJIUserAccount(context,
                            object : CommonCallbacks.CompletionCallbackWith<UserAccountState> {
                                override fun onSuccess(userAccountState: UserAccountState) {
                                    ToastUtils.setResultToToast("Login Successed!")
                                }

                                override fun onFailure(djiError: DJIError) {
                                    ToastUtils.setResultToToast("Login Failed!")
                                }
                            })
        }
    }

    private fun removeAppActivationListenerIfNeeded() {
        if (hasAppActivationListenerStarted.compareAndSet(true, false)) {
            AppActivationManager.getInstance().removeAppActivationStateListener(appActivationStateListener)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        removeFirmwareVersionListener()
        removeAppActivationListenerIfNeeded()
        mHandler?.removeCallbacksAndMessages(null)
        mHandlerUI?.removeCallbacksAndMessages(null)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            mHandlerThread.quitSafely()
        } else {
            mHandlerThread.quit()
        }
        mHandlerUI = null
        mHandler = null
    }


}
