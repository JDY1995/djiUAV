package com.architecture.common.help

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings

/**
 * Created by xuying on 2018/5/15.
 */
object SystemActivityManager{

    // 启动应用的设置
    fun startAppSettings(activity: Activity) {
        var intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.parse("package:" + activity.packageName)
        activity.startActivity(intent)
    }

    fun bluetoothSettings(context: Context) {
        val settintIntent = Intent(
                Settings.ACTION_BLUETOOTH_SETTINGS)
        settintIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(settintIntent)
    }

    fun diallPhone(context: Context, phoneNum: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        val data = Uri.parse("tel:$phoneNum")
        intent.data = data
        context.startActivity(intent)
    }


    @SuppressLint("MissingPermission")
    // 拨打电话
    fun callPhone(activity: Activity, phoneNum: String) {
        PermissionManager.phone(activity, {
            val intent = Intent(Intent.ACTION_CALL)
            val data = Uri.parse("tel:$phoneNum")
            intent.data = data
            activity.startActivity(intent)
        })
    }
}