package com.architecture.common.base

import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import com.apkfuns.logutils.LogUtils
import com.architecture.common.model.login.TokenInfo
import com.architecture.common.util.DESUtils
import com.architecture.common.util.SPUtils
import com.architecture.common.util.Util
import com.google.gson.Gson
import com.scwang.smartrefresh.header.MaterialHeader
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.tencent.bugly.crashreport.CrashReport
import dagger.android.DaggerApplication


abstract class BaseDaggerApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        init()
    }


    /**
     * 初始化数据
     */
    private fun init() {
        instance = this
        //初始化RefreshLayout
        initRefreshLayout()
        //开启InstantRun之后，一定要在ARouter.init之前调用openDebug
        if (Util.isDebug(this) || Util.isLog()) {

            ARouter.openDebug()
            ARouter.openLog()
            //输出日志
            LogUtils.getLogConfig()
                    .configAllowLog(true)
                    .configTagPrefix("xuying")
                    .configShowBorders(true)
                    .configFormatTag("%d{HH:mm:ss:SSS} %t %c{-5}")

        }
        ARouter.init(this)
        //腾讯统计
        CrashReport.initCrashReport(applicationContext, "389dc816a1", true)

    }

    companion object {
        var instance: BaseDaggerApplication? = null

        @Synchronized
        fun context(): Context {
            return instance!!.applicationContext
        }

        fun initRefreshLayout() {
            SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
                //                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white)//全局设置主题颜色
                MaterialHeader(context)
            }
            SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
                ClassicsFooter(context).setDrawableSize(20f)
            }
        }
    }

    var gson: Gson = Gson()
    /*数据加解密处理*/
    var desUtils: DESUtils = DESUtils()


    fun getVersionCode(): Int {
        var versionCode = 0//忽略保存的版本号
        var localVersionCode = 0//apk的版本号
        try {
            versionCode = SPUtils.getSp(context(), "version_info", "code_key", 0) as Int
            // 获取软件版本号，对应AndroidManifest.xml下android:versionCode
            localVersionCode = context().getPackageManager().getPackageInfo(
                    context().getPackageName(), 0).versionCode

            if (localVersionCode > versionCode) {
                versionCode = localVersionCode
            }


        } catch (e: Exception) {
            e.printStackTrace()
        }

        return versionCode
    }
}
