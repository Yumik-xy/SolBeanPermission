package top.yumik.libs.solbeanpermission.permission.special.v23

import android.content.Context
import android.content.Intent
import android.provider.Settings
import top.yumik.libs.solbeanpermission.constant.AndroidVersion
import top.yumik.libs.solbeanpermission.constant.Permission
import top.yumik.libs.solbeanpermission.constant.PhoneRom
import top.yumik.libs.solbeanpermission.permission.special.ISpecialPermissionCompat
import top.yumik.libs.solbeanpermission.utils.PermissionChecker
import top.yumik.libs.solbeanpermission.utils.PermissionUtils
import top.yumik.libs.solbeanpermission.utils.StringUtils.cutMiddle

internal class SystemAlertWindowCompat : ISpecialPermissionCompat {

    override val targetApi: Int = AndroidVersion.ANDROID_6

    override val permission: String = Permission.SYSTEM_ALERT_WINDOW

    override fun isGranted(context: Context): Boolean {
        return Settings.canDrawOverlays(context)
    }

    override fun getIntent(context: Context): Intent {
        if (PhoneRom.isXiaoMiMiui() &&
            (PhoneRom.getRomVersionName().cutMiddle('V', '.').toIntOrNull() ?: Int.MAX_VALUE) <= 10
        ) {
            return super.getIntent(context)
        }

        val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
        if (!AndroidVersion.isAndroid11OrAbove()) {
            intent.setData(PermissionUtils.getPackageUri(context))
        }
        if (PermissionChecker.checkIntentExists(context, intent)) {
            return intent
        }

        return super.getIntent(context)
    }
}