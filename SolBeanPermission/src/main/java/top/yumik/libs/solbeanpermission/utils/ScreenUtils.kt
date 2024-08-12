package top.yumik.libs.solbeanpermission.utils

import android.app.Activity
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.view.Surface
import top.yumik.libs.solbeanpermission.constant.AndroidVersion

object ScreenUtils {

    fun lockScreenOrientation(activity: Activity) {
        try {
            val currentOrientation = activity.resources.configuration.orientation
            val isScreenReverse = isScreenReverse(activity)
            when (currentOrientation) {
                Configuration.ORIENTATION_LANDSCAPE -> activity.requestedOrientation =
                    if (isScreenReverse) {
                        ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
                    } else {
                        ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                    }

                Configuration.ORIENTATION_PORTRAIT -> activity.requestedOrientation =
                    if (isScreenReverse) {
                        ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT
                    } else {
                        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    }

                else -> {
                    SolBeanLog.warn("锁定屏幕方向发生错误，当前屏幕状态不是横屏或竖屏状态")
                }
            }
        } catch (e: Exception) {
            SolBeanLog.warn("锁定屏幕方向发生错误: \n" + e.localizedMessage)
        }
    }

    fun unlockScreenOrientation(activity: Activity) {
        try {
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        } catch (e: Exception) {
            SolBeanLog.warn("解锁屏幕方向发生错误: \n" + e.localizedMessage)
        }
    }

    @JvmStatic
    fun isScreenReverse(activity: Activity): Boolean {
        val display = if (AndroidVersion.isAndroid11OrAbove()) {
            activity.display
        } else {
            activity.windowManager.defaultDisplay
        } ?: return false

        val rotation = display.rotation
        return rotation == Surface.ROTATION_180 || rotation == Surface.ROTATION_270
    }
}