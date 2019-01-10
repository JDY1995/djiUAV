package com.mvp.tfkj.lib.di

import android.content.Context
import com.tfkj.dajiang.uav.MainApplication
import dagger.Binds
import dagger.Module


/**
 * ####pplication需要根据使用情况进行修改####
 * Created by xuying on 2018/5/10
 */
@Module
abstract class ApplicationModule {
    //expose Application as an injectable context
    @Binds
    abstract fun bindContext(application: MainApplication): Context
}