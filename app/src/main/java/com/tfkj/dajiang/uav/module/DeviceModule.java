package com.tfkj.dajiang.uav.module;

import com.mvp.tfkj.lib.di.ActivityScoped;
import com.mvp.tfkj.lib.di.FragmentScoped;
import com.tfkj.dajiang.uav.contract.DeviceContract;
import com.tfkj.dajiang.uav.fragment.DeviceFragment;
import com.tfkj.dajiang.uav.presenter.DevicePresenter;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by xuying on 2019-1-2.
 */
@Module
public abstract class DeviceModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract DeviceFragment DeviceFragment();


    @ActivityScoped
    @Binds
    abstract DeviceContract.Presenter DevicePresenter(DevicePresenter presenter);

}