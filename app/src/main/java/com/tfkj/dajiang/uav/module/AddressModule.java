package com.tfkj.dajiang.uav.module;

import com.mvp.tfkj.lib.di.ActivityScoped;
import com.mvp.tfkj.lib.di.FragmentScoped;
import com.tfkj.dajiang.uav.contract.AddressContract;
import com.tfkj.dajiang.uav.fragment.AddressFragment;
import com.tfkj.dajiang.uav.presenter.AddressPresenter;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by xuying on 2019-1-2.
 */
@Module
public abstract class AddressModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract AddressFragment AddressFragment();


    @ActivityScoped
    @Binds
    abstract AddressContract.Presenter AddressPresenter(AddressPresenter presenter);

}