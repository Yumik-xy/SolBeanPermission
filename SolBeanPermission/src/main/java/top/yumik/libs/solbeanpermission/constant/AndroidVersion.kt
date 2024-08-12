package top.yumik.libs.solbeanpermission.constant

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast

/**
 * Android 版本号与版本码对照表
 */
@SuppressLint("ObsoleteSdkInt")
object AndroidVersion {

    const val ANDROID_14 = Build.VERSION_CODES.UPSIDE_DOWN_CAKE
    const val ANDROID_13 = Build.VERSION_CODES.TIRAMISU
    const val ANDROID_12_L = Build.VERSION_CODES.S_V2
    const val ANDROID_12 = Build.VERSION_CODES.S
    const val ANDROID_11 = Build.VERSION_CODES.R
    const val ANDROID_10 = Build.VERSION_CODES.Q
    const val ANDROID_9 = Build.VERSION_CODES.P
    const val ANDROID_8_1 = Build.VERSION_CODES.O_MR1
    const val ANDROID_8 = Build.VERSION_CODES.O
    const val ANDROID_7_1 = Build.VERSION_CODES.N_MR1
    const val ANDROID_7 = Build.VERSION_CODES.N
    const val ANDROID_6 = Build.VERSION_CODES.M
    const val ANDROID_1 = Build.VERSION_CODES.BASE

    /**
     * 获取 Android 版本号
     */
    @JvmStatic
    fun getAndroidVersionCode(): Int {
        return Build.VERSION.SDK_INT
    }

    /**
     * 获取 TargetSdk 版本号
     */
    @JvmStatic
    fun getTargetSdkVersionCode(context: Context): Int {
        return context.applicationInfo.targetSdkVersion
    }

    /**
     * 是否是 Android 14 以上的版本
     */
    @JvmStatic
    @ChecksSdkIntAtLeast(api = ANDROID_14)
    fun isAndroid14OrAbove(): Boolean {
        return Build.VERSION.SDK_INT >= ANDROID_14
    }

    /**
     * 是否是 Android 13 以上的版本
     */
    @JvmStatic
    @ChecksSdkIntAtLeast(api = ANDROID_13)
    fun isAndroid13OrAbove(): Boolean {
        return Build.VERSION.SDK_INT >= ANDROID_13
    }

    /**
     * 是否是 Android 12.1 以上的版本
     */
    @JvmStatic
    @ChecksSdkIntAtLeast(api = ANDROID_12_L)
    fun isAndroid12LOrAbove(): Boolean {
        return Build.VERSION.SDK_INT >= ANDROID_12_L
    }

    /**
     * 是否是 Android 12 以上的版本
     */
    @JvmStatic
    @ChecksSdkIntAtLeast(api = ANDROID_12)
    fun isAndroid12OrAbove(): Boolean {
        return Build.VERSION.SDK_INT >= ANDROID_12
    }

    /**
     * 是否是 Android 11 以上的版本
     */
    @JvmStatic
    @ChecksSdkIntAtLeast(api = ANDROID_11)
    fun isAndroid11OrAbove(): Boolean {
        return Build.VERSION.SDK_INT >= ANDROID_11
    }

    /**
     * 是否是 Android 10 以上的版本
     */
    @JvmStatic
    @ChecksSdkIntAtLeast(api = ANDROID_10)
    fun isAndroid10OrAbove(): Boolean {
        return Build.VERSION.SDK_INT >= ANDROID_10
    }

    /**
     * 是否是 Android 9 以上的版本
     */
    @JvmStatic
    @ChecksSdkIntAtLeast(api = ANDROID_9)
    fun isAndroid9OrAbove(): Boolean {
        return Build.VERSION.SDK_INT >= ANDROID_9
    }

    /**
     * 是否是 Android 8.1 以上的版本
     */
    @JvmStatic
    @ChecksSdkIntAtLeast(api = ANDROID_8_1)
    fun isAndroid8OOrAbove(): Boolean {
        return Build.VERSION.SDK_INT >= ANDROID_8_1
    }

    /**
     * 是否是 Android 8 以上的版本
     */
    @JvmStatic
    @ChecksSdkIntAtLeast(api = ANDROID_8)
    fun isAndroid8OrAbove(): Boolean {
        return Build.VERSION.SDK_INT >= ANDROID_8
    }

    /**
     * 是否是 Android 7.1 以上的版本
     */
    @JvmStatic
    @ChecksSdkIntAtLeast(api = ANDROID_7_1)
    fun isAndroid7NOrAbove(): Boolean {
        return Build.VERSION.SDK_INT >= ANDROID_7_1
    }

    /**
     * 是否是 Android 7 以上的版本
     */
    @JvmStatic
    @ChecksSdkIntAtLeast(api = ANDROID_7)
    fun isAndroid7OrAbove(): Boolean {
        return Build.VERSION.SDK_INT >= ANDROID_7
    }

    /**
     * 是否是 Android 6 以上的版本
     */
    @JvmStatic
    @ChecksSdkIntAtLeast(api = ANDROID_6)
    fun isAndroid6OrAbove(): Boolean {
        return Build.VERSION.SDK_INT >= ANDROID_6
    }
}