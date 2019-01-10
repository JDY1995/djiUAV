package com.mvp.tfkj.lib.di

import com.architecture.common.util.Util
import com.tfkj.dajiang.uav.MainApplication

import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit


/**
 * Created by xuying on 2018-6-19.
 */
class OkhttpJavaDefault constructor(application: MainApplication) {
    companion object {

        lateinit var okHttpClient: OkHttpClient
    }

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val loginInterceptor = Interceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
//                    .header("Authorization", application.tokenBean.tokenType + " " + application.tokenBean.accessToken)
                    .header("client", "android")
                    .header("edition", "" + application.getVersionCode())
                    .header("Accept", "application/json")
                    .method(original.method(), original.body())
            val request = requestBuilder.build()
            chain.proceed(request)
        }

        val userIdInterceptor = Interceptor { chain ->
            //post统一请求
            var original = chain.request()
            if (original.body() is FormBody) {
                val bodyBuilder = FormBody.Builder()

                var formBody = original.body() as FormBody

                for (i in 0 until formBody.size()) {
                    bodyBuilder.addEncoded(formBody.encodedName(i), formBody.encodedValue(i))
                }
                formBody = bodyBuilder
//                        .addEncoded("userOID", application.userBean.userId)
//                        .addEncoded("tokenId", application.tokenBean.accessToken)
                        .build()
                original = original.newBuilder().post(formBody).build()
            }
            chain.proceed(original)
            //get的统一请求
//            val original = chain.request()
//            val originalHttpUrl = original.url()
//            val url = originalHttpUrl.newBuilder()
//                    .addQueryParameter("userOID", application.userBean.userId)
//                    .addQueryParameter("tokenId", application.tokenBean.accessToken)
//                    .build()
//
//            // Request customization: add request headers
//            val requestBuilder = original.newBuilder()
//                    .url(url)
//            val request = requestBuilder.build()
//            chain.proceed(request)
        }
        var builder = OkHttpClient.Builder()
        if (Util.isDebug(application) ||
                Util.isLog()) {
            //添加日志输出
            builder.addNetworkInterceptor(interceptor)
        }
        okHttpClient = builder
                .addInterceptor(loginInterceptor)
                .addInterceptor(userIdInterceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(10, TimeUnit.SECONDS)//设置连接超时
                .readTimeout(30, TimeUnit.SECONDS)//读取超时
                .writeTimeout(30, TimeUnit.SECONDS)//写入超时
                .build()
    }

    fun getOkHttpClient(): OkHttpClient {
        return okHttpClient
    }
}