package com.tfkj.dajiang.uav.fragment

import android.app.Activity
import android.support.design.widget.TabLayout
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView

/**
 * Created by Administrator on 2018/3/5 0005.
 */
object TabLayoutHelp {
    /**
     * 设置tablayout
     */
    fun setTab(activity: Activity, tabs: TabLayout) {
        val tablayout = tabs.javaClass
        val tabStrip = tablayout.getDeclaredField("mTabStrip")
        tabStrip.isAccessible = true
        val ll_tab = tabStrip.get(tabs) as LinearLayout

        val windowWidth = activity.windowManager.defaultDisplay.width
        val childWidth = windowWidth / ll_tab.childCount
        for (i in 0 until ll_tab.childCount) {
            val child: View = ll_tab.getChildAt(i)
            val mTextViewField = child.javaClass.getDeclaredField("mTextView")
            mTextViewField.isAccessible = true
            val mTextView = mTextViewField.get(child) as TextView

            child.setPadding(0, 0, 0, 0)
            //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
            var width = 0
            width = mTextView.getWidth()
            if (width == 0) {
                mTextView.measure(0, 0)
                width = mTextView.getMeasuredWidth()
            }

            val params: LinearLayout.LayoutParams = child.layoutParams as LinearLayout.LayoutParams
            val margin = (childWidth - width) / 2
            params.leftMargin = margin
            params.rightMargin = margin
            child.layoutParams = params
            child.invalidate()
        }
    }
}