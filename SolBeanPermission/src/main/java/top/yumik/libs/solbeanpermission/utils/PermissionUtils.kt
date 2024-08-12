package top.yumik.libs.solbeanpermission.utils

import android.app.Activity
import android.app.AppOpsManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Handler
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import top.yumik.libs.solbeanpermission.constant.AndroidVersion
import top.yumik.libs.solbeanpermission.constant.PhoneRom
import top.yumik.libs.solbeanpermission.model.PermissionResult
import top.yumik.libs.solbeanpermission.permission.IPermissionCompat
import top.yumik.libs.solbeanpermission.permission.UnSupportPermissionCompatImp

internal object PermissionUtils {

    private val handler = Handler(Looper.getMainLooper())

    private const val CHECK_OP_NO_THROW = "checkOpNoThrow"

    /**
     * 判断是否是调试模式
     */
    fun isDebugMode(context: Context): Boolean {
        return (context.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0
    }

    /**
     * 获取当前包名的Uri
     */
    fun getPackageUri(context: Context): Uri {
        return Uri.parse("package:" + context.packageName)
    }

    /**
     * 系统标准Api，检查是否拥有权限
     */
    fun checkSelfPermission(context: Context, permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * 系统标准Api，检查是否拥有权限
     */
    fun checkOpNoThrow(context: Context, opName: String, defaultOpValue: Int): Boolean {
        val appOps = context.getSystemService<AppOpsManager>() ?: return true
        val appInfo = context.applicationInfo
        val packageName = context.packageName
        val uid = appInfo.uid

        return try {
            val appOpsClass = Class.forName(AppOpsManager::class.java.name)
            val checkOpNoThrowMethod = appOpsClass.getMethod(
                CHECK_OP_NO_THROW,
                Integer.TYPE,
                Integer.TYPE,
                String::class.java
            )
            val opValue: Int = try {
                appOpsClass.getDeclaredField(opName).getInt(null)
            } catch (_: NoSuchFieldException) {
                defaultOpValue
            }
            checkOpNoThrowMethod.invoke(
                appOps,
                opValue,
                uid,
                packageName
            ) as Int == AppOpsManager.MODE_ALLOWED
        } catch (e: Exception) {
            true
        }
    }

    /**
     * 系统标准Api，检查是否拥有权限
     */
    fun checkOpNoThrow(context: Context, opName: String): Boolean {
        val appOps = context.getSystemService<AppOpsManager>() ?: return true
        val appInfo = context.applicationInfo
        val packageName = context.packageName
        val uid = appInfo.uid

        return AppOpsManager.MODE_ALLOWED == if (AndroidVersion.isAndroid10OrAbove()) {
            appOps.unsafeCheckOpNoThrow(opName, uid, packageName)
        } else {
            appOps.checkOpNoThrow(opName, uid, packageName)
        }
    }

    /**
     * 需要使用 ActivityCompat 以修复 Android 12 调用 shouldShowRequestPermissionRationale 出现内存泄漏的问题
     *
     * @see <a href="https://github.com/androidx/androidx/pull/435">Android 12 shouldShowRequestPermissionRationale 内存泄漏</a>
     */
    fun shouldShowRequestPermissionRationale(activity: Activity, permission: String): Boolean {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)
    }

    /**
     * 特殊权限请求回调后，需要等待一段时间，避免读取权限时已经显示未获取
     */
    fun waitSpecialGranted(runnable: Runnable) {
        var delayMillis: Long = if (AndroidVersion.isAndroid11OrAbove()) 200 else 300

        if (PhoneRom.isHuaweiHarmonyOs() || PhoneRom.isHuaweiEmui()) {
            delayMillis = if (AndroidVersion.isAndroid8OrAbove()) 300 else 500
        } else if (PhoneRom.isXiaoMiMiui() && AndroidVersion.isAndroid11OrAbove()) {
            delayMillis = 1000
        }

        handler.postDelayed(runnable, delayMillis)
    }

    fun isSpecialPermission(permission: String): Boolean {
        return PermissionCompat.specialPermissionCompat.any { it.permission == permission }
    }

    fun isDangerousPermission(permission: String): Boolean {
        return PermissionCompat.dangerousPermissionCompat.any { it.permission == permission }
    }

    fun isPermissionSupport(permission: String): Boolean {
        return PermissionCompat.permissionToCompat.containsKey(permission)
    }

    fun getPermissionCompat(permission: String): IPermissionCompat {
        return PermissionCompat.permissionToCompat[permission]
            ?: UnSupportPermissionCompatImp(permission)
    }

    fun getPermissionResult(
        activity: Activity,
        permission: String,
        isGranted: Boolean? = null
    ): PermissionResult {
        val compat = getPermissionCompat(permission)
        return when {
            isGranted == true || compat.isGranted(activity) -> {
                PermissionResult.Granted
            }

            compat.isNeverAsk(activity) -> {
                PermissionResult.NeverAsk
            }

            else -> {
                PermissionResult.Denied
            }
        }
    }
}