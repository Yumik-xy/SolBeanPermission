package top.yumik.libs.solbeanpermission.permission.special.v23

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.core.content.getSystemService
import top.yumik.libs.solbeanpermission.constant.AndroidVersion
import top.yumik.libs.solbeanpermission.constant.Permission
import top.yumik.libs.solbeanpermission.permission.special.ISpecialPermissionCompat
import top.yumik.libs.solbeanpermission.utils.PermissionChecker
import top.yumik.libs.solbeanpermission.utils.PermissionUtils

internal class NotificationServiceCompat : ISpecialPermissionCompat {

    companion object {
        private const val OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION"
        private const val OP_POST_NOTIFICATION_DEFAULT = 11

        private const val ACTION_APP_NOTIFICATION_SETTINGS =
            "android.settings.APP_NOTIFICATION_SETTINGS"
        private const val EXTRA_APP_PACKAGE = "app_package"
        private const val EXTRA_APP_UID = "app_uid"
    }

    override val targetApi: Int = AndroidVersion.ANDROID_6

    override val permission: String = Permission.NOTIFICATION_SERVICE

    override fun isGranted(context: Context): Boolean {
        val result = if (AndroidVersion.isAndroid7OrAbove()) {
            context.getSystemService<NotificationManager>()?.areNotificationsEnabled()
        } else {
            PermissionUtils.checkOpNoThrow(
                context,
                OP_POST_NOTIFICATION,
                OP_POST_NOTIFICATION_DEFAULT
            )
        }
        return result ?: super.isGranted(context)
    }

    override fun getIntent(context: Context): Intent {
        val intent = if (AndroidVersion.isAndroid8OrAbove()) {
            Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
            }
        } else {
            Intent(ACTION_APP_NOTIFICATION_SETTINGS).apply {
                putExtra(EXTRA_APP_PACKAGE, context.packageName)
                putExtra(EXTRA_APP_UID, context.applicationInfo.uid)
            }
        }
        if (PermissionChecker.checkIntentExists(context, intent)) {
            return intent
        }

        return super.getIntent(context)
    }
}