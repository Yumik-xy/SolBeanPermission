package top.yumik.libs.solbeanpermission.permission.special.v23

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.core.content.getSystemService
import top.yumik.libs.solbeanpermission.constant.AndroidVersion
import top.yumik.libs.solbeanpermission.constant.Permission
import top.yumik.libs.solbeanpermission.constant.PhoneRom
import top.yumik.libs.solbeanpermission.permission.special.ISpecialPermissionCompat
import top.yumik.libs.solbeanpermission.utils.PermissionChecker
import top.yumik.libs.solbeanpermission.utils.PermissionUtils

internal class AccessNotificationPolicyCompat : ISpecialPermissionCompat {

    companion object {

        private const val ACTION_NOTIFICATION_POLICY_ACCESS_DETAIL_SETTINGS =
            "android.settings.NOTIFICATION_POLICY_ACCESS_DETAIL_SETTINGS"

    }

    override val targetApi: Int = AndroidVersion.ANDROID_6

    override val permission: String = Permission.ACCESS_NOTIFICATION_POLICY

    override fun isGranted(context: Context): Boolean {
        return context.getSystemService<NotificationManager>()
            ?.isNotificationPolicyAccessGranted
            ?: super.isGranted(context)
    }

    override fun getIntent(context: Context): Intent {
        // 华为HarmonyOS和荣耀MagicOS不支持直接跳转到详情设置页面
        val intent = if (!AndroidVersion.isAndroid10OrAbove() ||
            PhoneRom.isHuaweiHarmonyOs() ||
            PhoneRom.isHonorMagicOs()
        ) {
            Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
        } else {
            Intent(ACTION_NOTIFICATION_POLICY_ACCESS_DETAIL_SETTINGS).apply {
                data = PermissionUtils.getPackageUri(context)
            }
        }
        if (PermissionChecker.checkIntentExists(context, intent)) {
            return intent
        }

        return super.getIntent(context)
    }
}