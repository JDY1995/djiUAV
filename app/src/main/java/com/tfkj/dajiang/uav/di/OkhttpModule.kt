package com.mvp.tfkj.lib.di

import com.tfkj.dajiang.uav.MainApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


/**
 * okhttp请求
 * Created by xuying on 2018/4/26.
 */

@Module
class OkhttpModule {
//    @Singleton
//    @Provides
//    fun remoteOkHttpClient(application: MainApplication): OkHttpClient {
//        return OkhttpDefault(application).getOkHttpClient()
//    }

    @Singleton
    @Provides
    fun okhttpJavaDefault(application: MainApplication): OkhttpJavaDefault {
        return OkhttpJavaDefault(application)
    }

}