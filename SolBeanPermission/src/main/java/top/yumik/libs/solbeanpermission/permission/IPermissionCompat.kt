package top.yumik.libs.solbeanpermission.permission

import android.app.Activity
import android.content.Context
import android.content.Intent
import top.yumik.libs.solbeanpermission.model.PermissionConfig
import top.yumik.libs.solbeanpermission.utils.PermissionChecker
import top.yumik.libs.solbeanpermission.utils.PermissionUtils
import top.yumik.libs.solbeanpermission.utils.SolBeanLog

internal interface IPermissionCompat {

    /**
     * 权限新增的版本
     */
    val targetApi: Int

    /**
     * 权限名称
     */
    val permission: String

    /**
     * 判断权限是否获取
     */
    fun isGranted(context: Context): Boolean

    /**
     * 判断权限是否被标记为不再询问
     */
    fun isNeverAsk(activity: Activity): Boolean

    /**
     * 获取权限的启动的[Intent]
     */
    fun getIntent(context: Context): Intent

    /**
     * 检查权限配置是否合规
     */
    fun checkManifest(context: Context): Boolean {
        val check = PermissionChecker.checkManifestContainerPermission(context, permission)
        if (!check) {
            SolBeanLog.error(
                context,
                "请添加 <uses-permission android:name=\"$permission\" />  到应用的 AndroidManifest.xml"
            )
        }
        return check
    }

    /**
     * 权限的拓展配置，可以标识权限降级和权限预授权
     *
     * - [PermissionConfig.Replace] 降级方案
     * - [PermissionConfig.Advance] 预授权方案
     */
    fun config(context: Context): PermissionConfig {
        return PermissionConfig.None
    }
}

internal fun IPermissionCompat.isSpecialPermission(): Boolean {
    return PermissionUtils.isSpecialPermission(permission)
}

internal fun IPermissionCompat.isDangerousPermission(): Boolean {
    return PermissionUtils.isDangerousPermission(permission)
}