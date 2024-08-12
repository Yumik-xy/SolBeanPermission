package top.yumik.libs.solbeanpermission.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import top.yumik.libs.solbeanpermission.constant.AndroidVersion

internal object PermissionChecker {

    /**
     * 检查Intent是否存在
     */
    fun checkIntentExists(context: Context, intent: Intent?): Boolean {
        if (intent == null) {
            return false
        }

        val packageManager = context.packageManager
        if (AndroidVersion.isAndroid13OrAbove()) {
            val flag =
                PackageManager.ResolveInfoFlags.of(PackageManager.MATCH_DEFAULT_ONLY.toLong())
            return packageManager.queryIntentActivities(intent, flag).isNotEmpty()
        }

        return packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
            .isNotEmpty()
    }

    /**
     * 检查Class是否存在
     */
    fun checkClassExists(className: String): Boolean {
        return runCatching { Class.forName(className) }.isSuccess
    }

    /**
     * 检查权限是否在配置中
     */
    fun checkManifestContainerPermission(
        context: Context,
        checkPermission: String
    ): Boolean {
        return AndroidManifestParseUtils.parse(context).usesPermissions.any { it.name == checkPermission }
    }

    /**
     * 检查权限中是否配置了`requestLegacyExternalStorage`
     */
    fun checkManifestRequestLegacyExternalStorage(context: Context): Boolean {
        return AndroidManifestParseUtils.parse(context).application?.requestLegacyExternalStorage == true
    }

    /**
     * 检查蓝牙扫描权限是否配置了不推导地理位置
     */
    fun checkManifestPermissionNeverForLocation(context: Context, permissions: String): Boolean {
        return AndroidManifestParseUtils.parse(context)
            .usesPermissions
            .firstOrNull { it.name == permissions }
            ?.isNeverForLocation == true
    }
}