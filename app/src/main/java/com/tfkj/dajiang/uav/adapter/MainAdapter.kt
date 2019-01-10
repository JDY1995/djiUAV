package com.tfkj.dajiang.uav.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.mvp.tfkj.lib.di.ActivityScoped
import com.tfkj.dajiang.uav.fragment.ShowFragment
import com.tfkj.dajiang.uav.fragment.UploadFragment
import com.tfkj.dajiang.uav.fragment.UserFragment
import javax.inject.Inject


/**
 * Created by Administrator on 2018/1/8.
 */
@ActivityScoped
class MainAdapter @Inject constructor(fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {
    private val mTitle: Array<String> = arrayOf("视频连接", "资料上传", "个人中心")
    @Inject
    lateinit var mShowFragment: ShowFragment
    @Inject
    lateinit var mUploadFragment: UploadFragment
    @Inject
    lateinit var mUserFragment: UserFragment

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return mShowFragment
            1 -> return mUploadFragment
            2 -> return mUserFragment
        }
        return mShowFragment
    }

    override fun getCount(): Int {
        return mTitle.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return mTitle[position]


    }

}