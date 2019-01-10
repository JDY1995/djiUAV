package com.mvp.tfkj.lib.di

import com.architecture.common.base.BaseDaggerApplication
import com.architecture.common.util.Util
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by TangTz on 2018/6/6.
 */
class RetrofitJavaDefault(okHttpClient: OkhttpJavaDefault) {
    companion object {

        lateinit var retrofit: Retrofit
    }

    init {
        var baseUrl = ""

        if (Util.isDebug(BaseDaggerApplication.context())) {
//            baseUrl = WebConstants.BASE_JAVA_URL_TEST
        }
        if (Util.isPrepare()) {
//            baseUrl = WebConstants.BASE_JAVA_URL_PREPARE
        }
        if (Util.isEasyMock()) {
//            baseUrl = WebConstants.BASE_JAVA_URL_EASYMOCK
        }

        if (baseUrl == "") {
//            baseUrl = WebConstants.BASE_JAVA_URL
        }

        retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient.getOkHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }


    fun getRetrofit(): Retrofit {
        return retrofit
    }


}