package com.tfkj.dajiang.uav.module;

import com.mvp.tfkj.lib.di.ActivityScoped;
import com.mvp.tfkj.lib.di.FragmentScoped;
import com.tfkj.dajiang.uav.contract.LoginContract;
import com.tfkj.dajiang.uav.fragment.LoginFragment;
import com.tfkj.dajiang.uav.presenter.LoginPresenter;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by xuying on 2019-1-2.
 */
@Module
public abstract class LoginModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract LoginFragment LoginFragment();


    @ActivityScoped
    @Binds
    abstract LoginContract.Presenter LoginPresenter(LoginPresenter presenter);

}