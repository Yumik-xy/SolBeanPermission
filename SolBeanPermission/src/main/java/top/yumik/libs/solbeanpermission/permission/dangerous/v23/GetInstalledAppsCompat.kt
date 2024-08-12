package top.yumik.libs.solbeanpermission.permission.dangerous.v23

import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.PermissionInfo
import android.provider.Settings
import android.provider.Settings.SettingNotFoundException
import top.yumik.libs.solbeanpermission.constant.AndroidVersion
import top.yumik.libs.solbeanpermission.constant.Permission
import top.yumik.libs.solbeanpermission.permission.dangerous.IDangerousPermissionCompat
import top.yumik.libs.solbeanpermission.utils.PermissionChecker
import top.yumik.libs.solbeanpermission.utils.PermissionUtils

internal class GetInstalledAppsCompat : IDangerousPermissionCompat {

    companion object {

        private const val OEM_INSTALLED_APPS_RUNTIME_PERMISSION_ENABLE =
            "oem_installed_apps_runtime_permission_enable"

        private const val CHECKER_PERMISSION_QUERY_ALL_PACKAGES = Permission.QUERY_ALL_PACKAGES
    }

    override val targetApi: Int = AndroidVersion.ANDROID_6

    override val permission: String = Permission.GET_INSTALLED_APPS

    override fun isGranted(context: Context): Boolean {
        if (isSupportGetInstalledAppsPermission(context)) {
            return PermissionUtils.checkSelfPermission(context, permission)
        }

        // 如果不支持，那也没法判断了
        return true
    }

    private fun isSupportGetInstalledAppsPermission(context: Context): Boolean {
        try {
            val permissionInfo = context.packageManager.getPermissionInfo(permission, 0)
            if (permissionInfo != null) {
                return if (AndroidVersion.isAndroid9OrAbove()) {
                    permissionInfo.protection == PermissionInfo.PROTECTION_DANGEROUS
                } else {
                    permissionInfo.protectionLevel and PermissionInfo.PROTECTION_MASK_BASE == PermissionInfo.PROTECTION_DANGEROUS
                }
            }
        } catch (_: PackageManager.NameNotFoundException) {
        }

        try {
            // 标准流程如下：
            // http://www.taf.org.cn/upload/AssociationStandard/TTAF%20108-2022%20%E7%A7%BB%E5%8A%A8%E7%BB%88%E7%AB%AF%E5%BA%94%E7%94%A8%E8%BD%AF%E4%BB%B6%E5%88%97%E8%A1%A8%E6%9D%83%E9%99%90%E5%AE%9E%E6%96%BD%E6%8C%87%E5%8D%97.pdf
            return Settings.Secure.getInt(
                context.contentResolver,
                OEM_INSTALLED_APPS_RUNTIME_PERMISSION_ENABLE
            ) == 1
        } catch (_: SettingNotFoundException) {
        }

        return false
    }

    override fun checkManifest(context: Context): Boolean {
        val hasQueryAllPackageConfig = PermissionChecker.checkManifestContainerPermission(
            context,
            CHECKER_PERMISSION_QUERY_ALL_PACKAGES
        )
        return hasQueryAllPackageConfig && super.checkManifest(context)
    }
}