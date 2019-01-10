package com.architecture.common.help

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics

/**
 * 显示分辨率配置，在主界面调用之后，为静态获取比例
 *
 * @author 徐映 2014-1-7
 */
class DisplayManager(activity: Activity) {

    /**
     * 获取比例系数
     *
     * @return
     */
    var scale: Float = 0.toFloat()
        private set

    private var xScale: Float = 0.toFloat()
    private var yScale: Float = 0.toFloat()
    val width: Int
    val height: Int

    val density: Float

    init {

        val dm = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(dm)
        width = dm.widthPixels
        height = dm.heightPixels
        setScale(activity)
        density = activity.resources.displayMetrics.density

    }

    private fun setScale(activity: Activity): Float {
        val metrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(metrics)

        xScale = metrics.widthPixels.toFloat() / 640f
        yScale = metrics.heightPixels.toFloat() / 960f
        scale = Math.min(xScale, yScale)
        // scale = Math.min(scale, 1);
        return scale
    }

    fun getxScale(): Float {
        return xScale
    }

    fun getyScale(): Float {
        return yScale
    }

    companion object {

        /** * 根据手机的分辨率从 dp 的单位 转成为 px(像素)  */
        fun dip2px(context: Context, dpValue: Float): Int {

            val scale = context.resources.displayMetrics.density
            return (dpValue * scale + 0.5f).toInt()
        }

        /** * 根据手机的分辨率从 px(像素) 的单位 转成为 dp  */
        fun px2dip(context: Context, pxValue: Float): Int {
            val scale = context.resources.displayMetrics.density
            return (pxValue / scale + 0.5f).toInt()
        }
    }

}
