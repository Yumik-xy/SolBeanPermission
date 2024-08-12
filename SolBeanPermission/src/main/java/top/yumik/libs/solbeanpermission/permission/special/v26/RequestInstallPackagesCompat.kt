package top.yumik.libs.solbeanpermission.permission.special.v26

import android.content.Context
import android.content.Intent
import android.provider.Settings
import top.yumik.libs.solbeanpermission.constant.AndroidVersion
import top.yumik.libs.solbeanpermission.constant.Permission
import top.yumik.libs.solbeanpermission.permission.special.ISpecialPermissionCompat
import top.yumik.libs.solbeanpermission.utils.PermissionChecker
import top.yumik.libs.solbeanpermission.utils.PermissionUtils

internal class RequestInstallPackagesCompat : ISpecialPermissionCompat {

    override val targetApi: Int = AndroidVersion.ANDROID_8

    override val permission: String = Permission.REQUEST_INSTALL_PACKAGES

    override fun isGranted(context: Context): Boolean {
        if (AndroidVersion.isAndroid8OrAbove()) {
            return context.packageManager.canRequestPackageInstalls()
        }

        return super.isGranted(context)
    }

    override fun getIntent(context: Context): Intent {
        if (AndroidVersion.isAndroid8OrAbove()) {
            val intent = Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES)
            intent.setData(PermissionUtils.getPackageUri(context))
            if (PermissionChecker.checkIntentExists(context, intent)) {
                return intent
            }
        }

        return super.getIntent(context)
    }
}