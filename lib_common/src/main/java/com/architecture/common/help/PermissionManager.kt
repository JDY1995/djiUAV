package com.architecture.common.help

import android.Manifest
import android.app.Activity
import android.widget.Toast
import com.tbruyelle.rxpermissions2.Permission
import com.tbruyelle.rxpermissions2.RxPermissions

/**
 * Created by Administrator on 2018/3/1 0001.
 */
object PermissionManager {

    /**
     * 危险权限
     * group:android.permission-group.CONTACTS
    permission:android.permission.WRITE_CONTACTS
    permission:android.permission.GET_ACCOUNTS
    permission:android.permission.READ_CONTACTS

    group:android.permission-group.PHONE
    permission:android.permission.READ_CALL_LOG
    permission:android.permission.READ_PHONE_STATE
    permission:android.permission.CALL_PHONE
    permission:android.permission.WRITE_CALL_LOG
    permission:android.permission.USE_SIP
    permission:android.permission.PROCESS_OUTGOING_CALLS
    permission:com.android.voicemail.permission.ADD_VOICEMAIL

    group:android.permission-group.CALENDAR
    permission:android.permission.READ_CALENDAR
    permission:android.permission.WRITE_CALENDAR

    group:android.permission-group.CAMERA
    permission:android.permission.CAMERA

    group:android.permission-group.SENSORS
    permission:android.permission.BODY_SENSORS

    group:android.permission-group.LOCATION
    permission:android.permission.ACCESS_FINE_LOCATION
    permission:android.permission.ACCESS_COARSE_LOCATION

    group:android.permission-group.STORAGE
    permission:android.permission.READ_EXTERNAL_STORAGE
    permission:android.permission.WRITE_EXTERNAL_STORAGE

    group:android.permission-group.MICROPHONE
    permission:android.permission.RECORD_AUDIO

    group:android.permission-group.SMS
    permission:android.permission.READ_SMS
    permission:android.permission.RECEIVE_WAP_PUSH
    permission:android.permission.RECEIVE_MMS
    permission:android.permission.RECEIVE_SMS
    permission:android.permission.SEND_SMS
    permission:android.permission.READ_CELL_BROADCASTS
     */
    //说明requestEach用于每个请求都执行
    //request多请求统一处理，直接返回boolean

    fun subscribe(activity: Activity, permission: Permission, success: () -> Unit) {
        when {
            permission.granted -> {
                // 用户已经同意该权限
                success()
            }
            permission.shouldShowRequestPermissionRationale -> {
                // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                Toast.makeText(activity, "您没有授权该权限，请在设置中打开授权", Toast.LENGTH_SHORT).show()
                SystemActivityManager.startAppSettings(activity)
            }
            else -> {
                // 用户拒绝了该权限，并且选中『不再询问』
                Toast.makeText(activity, "您没有授权该权限，请在设置中打开授权", Toast.LENGTH_SHORT).show()
                SystemActivityManager.startAppSettings(activity)
            }
        }
    }

    fun subscribe(activity: Activity, boolean: Boolean, success: () -> Unit) {
        when {
            boolean -> {
                // 用户已经同意该权限
                success()
            }
            else -> {
                // 用户拒绝了该权限，并且选中『不再询问』
                Toast.makeText(activity, "您没有授权该权限，请在设置中打开授权", Toast.LENGTH_SHORT).show()
                SystemActivityManager.startAppSettings(activity)
            }
        }
    }

    //电话
    fun phone(activity: Activity, success: () -> Unit) {
        val rxPermissions = RxPermissions(activity) // where this is an Activity instance
        //权限名称，多个权限之间逗号分隔开
        rxPermissions.requestEach(Manifest.permission.CALL_PHONE)
                .subscribe { permission ->
                    subscribe(activity, permission, success)
                }
    }

    //相机
    fun camera(activity: Activity, success: () -> Unit) {
        val rxPermissions = RxPermissions(activity) // where this is an Activity instance
        //权限名称，多个权限之间逗号分隔开
        rxPermissions.requestEach(Manifest.permission.CAMERA)
                .subscribe { permission ->
                    subscribe(activity, permission, success)
                }
    }

    //通讯录
    fun contacts(activity: Activity, success: () -> Unit) {
        val rxPermissions = RxPermissions(activity) // where this is an Activity instance
        //权限名称，多个权限之间逗号分隔开
        rxPermissions.request(Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS)
                .subscribe { permission ->
                    subscribe(activity, permission, success)
                }
    }

    //定位
    fun location(activity: Activity, success: () -> Unit) {
        val rxPermissions = RxPermissions(activity) // where this is an Activity instance
        //权限名称，多个权限之间逗号分隔开
        rxPermissions.request(Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe { permission ->
                    subscribe(activity, permission, success)
                }
    }

    //存储
    fun storage(activity: Activity, success: () -> Unit) {
        val rxPermissions = RxPermissions(activity) // where this is an Activity instance
        //权限名称，多个权限之间逗号分隔开
        rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe { permission ->
                    subscribe(activity, permission, success)
                }
    }

    //麦克风
    fun audio(activity: Activity, success: () -> Unit) {
        val rxPermissions = RxPermissions(activity) // where this is an Activity instance
        //权限名称，多个权限之间逗号分隔开
        rxPermissions.requestEach(Manifest.permission.RECORD_AUDIO)
                .subscribe { permission ->
                    subscribe(activity, permission, success)
                }
    }

    //相机和存储
    fun cameraAndStorage(activity: Activity, success: () -> Unit) {
        val rxPermissions = RxPermissions(activity) // where this is an Activity instance
        //权限名称，多个权限之间逗号分隔开
        rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe { permission ->
                    subscribe(activity, permission, success)
                }
    }

    //相机和存储加麦克风
    fun cameraStorageAudio(activity: Activity, success: () -> Unit) {
        val rxPermissions = RxPermissions(activity) // where this is an Activity instance
        //权限名称，多个权限之间逗号分隔开
        rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO)
                .subscribe { permission ->
                    subscribe(activity, permission, success)
                }
    }

    //安装apk权限
    fun installAPK(activity: Activity, success: () -> Unit) {
        val rxPermissions = RxPermissions(activity) // where this is an Activity instance
        //权限名称，多个权限之间逗号分隔开
        rxPermissions.request(Manifest.permission.REQUEST_INSTALL_PACKAGES)
                .subscribe { permission ->
                    subscribe(activity, permission, success)
                }
    }
}