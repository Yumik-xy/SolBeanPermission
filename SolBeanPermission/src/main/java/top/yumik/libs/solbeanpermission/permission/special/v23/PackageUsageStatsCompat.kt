package top.yumik.libs.solbeanpermission.permission.special.v23

import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.provider.Settings
import top.yumik.libs.solbeanpermission.constant.AndroidVersion
import top.yumik.libs.solbeanpermission.constant.Permission
import top.yumik.libs.solbeanpermission.permission.special.ISpecialPermissionCompat
import top.yumik.libs.solbeanpermission.utils.PermissionChecker
import top.yumik.libs.solbeanpermission.utils.PermissionUtils

internal class PackageUsageStatsCompat : ISpecialPermissionCompat {

    override val targetApi: Int = AndroidVersion.ANDROID_6

    override val permission: String = Permission.PACKAGE_USAGE_STATS

    override fun isGranted(context: Context): Boolean {
        return PermissionUtils.checkOpNoThrow(context, AppOpsManager.OPSTR_GET_USAGE_STATS)
    }

    override fun getIntent(context: Context): Intent {
        val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
        if (AndroidVersion.isAndroid10OrAbove()) {
            intent.setData(PermissionUtils.getPackageUri(context))
        }
        if (PermissionChecker.checkIntentExists(context, intent)) {
            return intent
        }

        return super.getIntent(context)
    }
}