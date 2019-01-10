package com.architecture.common.base.activity

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.support.annotation.Nullable
import android.text.Layout
import android.view.View
import android.view.View.*
import android.widget.TextView
import com.architecture.common.R
import com.jakewharton.rxbinding2.view.RxView
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import io.reactivex.functions.Consumer
import java.util.concurrent.TimeUnit


/**
 * created by xuying at 2018/1/24
 */
abstract class BaseActivity : RxAppCompatActivity() {

    @Nullable
    var tvTitle: TextView? = null
    //可以为空右侧标题文字
    @Nullable
    var tvRight: TextView? = null
    //可以为空左侧标题文字
    @Nullable
    var tvLeft: TextView? = null
    @Nullable
    var toolbar: View? = null

    lateinit var mActivity: Activity


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(initLayoutId())
        mActivity = this

        tvTitle = findViewById(R.id.tvTitle)
        tvRight = findViewById(R.id.tvRight)
        tvLeft = findViewById(R.id.tvLeft)
        toolbar = findViewById(R.id.toolbar)

        tvRight?.visibility = INVISIBLE

        showLeft()
    }


    protected abstract fun initLayoutId(): Int

    /**
     * ActionBar返回键
     *
     * @return
     */
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun setTitle(value: String) {
        tvTitle?.text = value
    }

    fun hideTitle() {
        toolbar?.visibility = GONE
    }

    fun showLeft() {
        tvLeft?.visibility = View.VISIBLE
        if (tvLeft != null)
            RxView.clicks(tvLeft!!).throttleFirst(300, TimeUnit.MILLISECONDS)   //防抖操作
                    .subscribe({
                        onBackPressed()
                    })
    }

    fun hideLeft() {
        tvLeft?.visibility = View.INVISIBLE
    }


    fun setTitleRight(value: String, id: Int, onNext: Consumer<Any>?) {
        tvRight?.visibility = VISIBLE
        tvRight?.text = value
        tvRight?.setCompoundDrawablesWithIntrinsicBounds(0, 0, id, 0)

        if (tvRight != null && onNext != null)
            RxView.clicks(tvRight!!).throttleFirst(300, TimeUnit.MILLISECONDS)   //防抖操作
                    .subscribe(onNext)
    }

    fun setTitleRight(value: String, onNext: Consumer<Any>?) {
        setTitleRight(value, 0, onNext)
    }

    fun setTitleRight(id: Int, onNext: Consumer<Any>?) {
        setTitleRight("", id, onNext)
    }

    fun setTitleRightColor(color: String) {
        tvRight?.setTextColor(Color.parseColor(color))
    }

    fun setTitleRightColor(color: Int) {
        tvRight?.setTextColor(mActivity.resources.getColor(color))
    }

    fun setTitleRightShow(isShow: Boolean) {
        if (isShow) {
            tvRight?.visibility = View.VISIBLE
        } else {
            tvRight?.visibility = View.INVISIBLE
        }
    }

}