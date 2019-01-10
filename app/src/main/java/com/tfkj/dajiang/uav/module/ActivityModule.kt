package com.mvp.tfkj.login.module

import android.location.Address
import com.mvp.tfkj.lib.di.ActivityScoped
import com.tfkj.dajiang.uav.activity.AddressActivity
import com.tfkj.dajiang.uav.activity.DeviceActivity
import com.tfkj.dajiang.uav.activity.LoginActivity
import com.tfkj.dajiang.uav.activity.MainActivity
import com.tfkj.dajiang.uav.module.AddressModule
import com.tfkj.dajiang.uav.module.DeviceModule
import com.tfkj.dajiang.uav.module.LoginModule
import com.tfkj.dajiang.uav.module.MainModule
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityModule {


    @ActivityScoped
    @ContributesAndroidInjector(modules = arrayOf(LoginModule::class))
    abstract fun loginActivity(): LoginActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = arrayOf(MainModule::class))
    abstract fun mainActivity(): MainActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = arrayOf(DeviceModule::class))
    abstract fun deviceActivity(): DeviceActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = arrayOf(AddressModule::class))
    abstract fun addressActivity(): AddressActivity


}
