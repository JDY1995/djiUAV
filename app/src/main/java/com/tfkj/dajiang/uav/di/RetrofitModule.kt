package com.mvp.tfkj.lib.di

import com.architecture.common.base.BaseDaggerApplication
import com.architecture.common.util.Util
import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient

/**
 * 根据不同的baseURL生成不同的retrofit请求
 * Created by xuying on 2018/4/26.
 */

@Module
class RetrofitModule {

    @Singleton
    @Provides
    fun retrofitJavaDefault(okHttpClient: OkhttpJavaDefault): RetrofitJavaDefault {
        return RetrofitJavaDefault(okHttpClient)
    }


}