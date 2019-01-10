package com.tfkj.dajiang.uav.module;

import android.support.v4.app.FragmentManager;

import com.mvp.tfkj.lib.di.ActivityScoped;
import com.mvp.tfkj.lib.di.FragmentScoped;
import com.tfkj.dajiang.uav.activity.MainActivity;
import com.tfkj.dajiang.uav.contract.MainContract;
import com.tfkj.dajiang.uav.contract.ShowContract;
import com.tfkj.dajiang.uav.contract.UploadContract;
import com.tfkj.dajiang.uav.contract.UserContract;
import com.tfkj.dajiang.uav.fragment.MainFragment;
import com.tfkj.dajiang.uav.fragment.ShowFragment;
import com.tfkj.dajiang.uav.fragment.UploadFragment;
import com.tfkj.dajiang.uav.fragment.UserFragment;
import com.tfkj.dajiang.uav.presenter.MainPresenter;
import com.tfkj.dajiang.uav.presenter.ShowPresenter;
import com.tfkj.dajiang.uav.presenter.UploadPresenter;
import com.tfkj.dajiang.uav.presenter.UserPresenter;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;


@Module
public abstract class MainModule {

    @Provides
    @ActivityScoped
    static FragmentManager fragmentManager(MainActivity activity) {
        return activity.getSupportFragmentManager();
    }

    @FragmentScoped
    @ContributesAndroidInjector
    abstract MainFragment MainFragment();


    @ActivityScoped
    @Binds
    abstract MainContract.Presenter MainPresenter(MainPresenter presenter);


    @FragmentScoped
    @ContributesAndroidInjector
    abstract ShowFragment ShowFragment();


    @ActivityScoped
    @Binds
    abstract ShowContract.Presenter ShowPresenter(ShowPresenter presenter);

    @FragmentScoped
    @ContributesAndroidInjector
    abstract UploadFragment UploadFragment();


    @ActivityScoped
    @Binds
    abstract UploadContract.Presenter UploadPresenter(UploadPresenter presenter);

    @FragmentScoped
    @ContributesAndroidInjector
    abstract UserFragment UserFragment();


    @ActivityScoped
    @Binds
    abstract UserContract.Presenter UserPresenter(UserPresenter presenter);
}
