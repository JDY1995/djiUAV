package com.architecture.common.base.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.architecture.common.base.activity.BaseDaggerActivity
import com.architecture.common.base.interf.IBaseView
import com.architecture.common.base.interf.IPresenter
import com.architecture.common.base.interf.IView
import com.trello.rxlifecycle2.components.support.RxFragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import me.leefeng.promptlibrary.PromptDialog
import java.util.concurrent.TimeUnit
import javax.inject.Inject


/**
 *
 * @param V 这个组中的成员的类型。继承
 * @see IView
 * @param P 这个组中的成员的类型。继承
 * @see IPresenter
 * @constructor 创建一个注入的fragment
 * created by xuying at 2018/1/24
 *
 */
abstract class BaseDaggerFragment<V : IView, P : IPresenter<V>> : RxFragment(), HasSupportFragmentInjector, IBaseView {
    //是否是懒加载
    var isLazyLoad = false

    //dagger2注入代码
    @Inject
    lateinit var childFragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun isActive(): Boolean {
        return isAdded
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment>? {
        return childFragmentInjector
    }


    protected lateinit var mInflater: LayoutInflater
    // 对应显示的view
    protected lateinit var mView: View
    protected lateinit var mActivity: BaseDaggerActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = activity as BaseDaggerActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        this.mInflater = inflater
        //设置UI
        mView = mInflater.inflate(initLayoutId(), null)
        return mView
    }

    /**
     * 设置界面ID
     */
    abstract fun initLayoutId(): Int

    //创建View的时候初始化
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //初始化界面,可用于初始化标题，初始化监听器等
        initView()
    }

    protected abstract fun initView()


    //对应默认对话框内容，不喜欢可以重写
    var dialog: PromptDialog? = null
    //是否隐藏了对话框
    var isHideDialog = false

    override fun showWaitDialog(content: String) {
        isHideDialog = false
        hideInputMethod()
        Observable.timer(200, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    //没有隐藏对话框，才显示对话框
                    if (!isHideDialog) {
                        dialog = dialog ?: PromptDialog(mActivity)
                        dialog?.run {
                            showLoading(content)
                        }
                    }
                }

    }

    override fun hideDialog() {
        isHideDialog = true
        dialog?.dismissImmediately()

    }

    override fun showError(value: String) {
        showDialog(value, 1)
    }

    override fun showSuccess(value: String) {
        showDialog(value, 0)
    }

    override fun showInfo(value: String) {
        showDialog(value, 2)
    }


    //隐藏输入法
    open fun hideInputMethod() {
        (mActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                .hideSoftInputFromWindow(mView.applicationWindowToken, 0)

    }

    //判断输入法是否已经打开
    fun isInputMethodOpened(context: Context): Boolean {
        val inputMethodManager = context.applicationContext
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return inputMethodManager.isActive
    }

    //提示对话框不能重复
    var mShowDialog: PromptDialog? = null

    private fun showDialog(value: String, type: Int) {
        hideInputMethod()

        mShowDialog = PromptDialog(mActivity)
        mShowDialog?.defaultBuilder
                ?.stayDuration(2000)
                ?.loadingDuration(1000)
        Observable.timer(300, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    mShowDialog?.dismissImmediately()
                    when (type) {
                        0 -> mShowDialog?.showSuccess(value, false)
                        1 -> mShowDialog?.showError(value, false)
                        2 -> mShowDialog?.showInfo(value, false)
                    }
                }

    }

    override fun finishActivity() {
        mActivity.finish()
    }

    /**
     * 懒加载实现
     */
    var isViewInitiated = false
    var isVisibleToUser = false
    var isDataInitiated = false

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isViewInitiated = true
        prepareFetchData()
    }

    //add,hide时的懒加载
    override fun onHiddenChanged(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        this.isVisibleToUser = !isVisibleToUser
        prepareFetchData()
    }

    //FragmentPagerAdapter+ViewPage时的懒加载
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        this.isVisibleToUser = isVisibleToUser
        prepareFetchData()
    }

    //懒加载刷新数据
    open fun lazyLoad() {

    }

    //不强制刷新懒加载
    open fun prepareFetchData(): Boolean {
        return prepareFetchData(false)
    }

    protected fun prepareFetchData(forceUpdate: Boolean): Boolean {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            lazyLoad()
            isDataInitiated = true
            return true
        }
        return false
    }

}
