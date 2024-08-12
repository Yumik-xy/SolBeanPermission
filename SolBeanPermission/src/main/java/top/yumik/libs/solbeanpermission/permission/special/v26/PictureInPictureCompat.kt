package top.yumik.libs.solbeanpermission.permission.special.v26

import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import top.yumik.libs.solbeanpermission.constant.AndroidVersion
import top.yumik.libs.solbeanpermission.constant.Permission
import top.yumik.libs.solbeanpermission.permission.special.ISpecialPermissionCompat
import top.yumik.libs.solbeanpermission.utils.PermissionChecker
import top.yumik.libs.solbeanpermission.utils.PermissionUtils

internal class PictureInPictureCompat : ISpecialPermissionCompat {

    companion object {

        private const val ACTION_PICTURE_IN_PICTURE_SETTINGS =
            "android.settings.PICTURE_IN_PICTURE_SETTINGS"
    }

    override val targetApi: Int = AndroidVersion.ANDROID_8

    override val permission: String = Permission.PICTURE_IN_PICTURE

    override fun isGranted(context: Context): Boolean {
        if (AndroidVersion.isAndroid8OrAbove()) {
            return PermissionUtils.checkOpNoThrow(context, AppOpsManager.OPSTR_PICTURE_IN_PICTURE)
        }

        return super.isGranted(context)
    }

    override fun getIntent(context: Context): Intent {
        val intent = Intent(ACTION_PICTURE_IN_PICTURE_SETTINGS)
        intent.setData(PermissionUtils.getPackageUri(context))
        if (PermissionChecker.checkIntentExists(context, intent)) {
            return intent
        }

        return super.getIntent(context)
    }
}