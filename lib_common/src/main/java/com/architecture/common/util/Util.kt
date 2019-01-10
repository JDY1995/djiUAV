package com.architecture.common.util

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.pm.PackageManager.NameNotFoundException

/**
 * Created by Administrator on 2018/4/27.
 */
object Util {
    /**
     * 判断App是否是Debug版本
     *
     * @return `true`: 是<br></br>`false`: 否
     */
    fun isDebug(context: Context): Boolean {
        return false
        if (context.packageName.isNullOrEmpty()) return false
        try {
            val pm = context.packageManager
            val ai = pm.getApplicationInfo(context.packageName, 0)
            return ai != null && ai!!.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
        } catch (e: NameNotFoundException) {
            e.printStackTrace()
            return false
        }

    }

    /**
     * 是否是预上线
     */
    fun isPrepare(): Boolean {
        return false
//        return true
    }

    /**
     * 假数据服务器
     */
    fun isEasyMock(): Boolean {
        return false
//        return true
    }

    /**
     *  是否输出日志
     */
    fun isLog(): Boolean {
//        return false
        return true
    }


}