package top.yumik.libs.solbeanpermission.permission.special.v23

import android.content.Context
import android.content.Intent
import android.net.VpnService
import top.yumik.libs.solbeanpermission.constant.AndroidVersion
import top.yumik.libs.solbeanpermission.constant.Permission
import top.yumik.libs.solbeanpermission.permission.special.ISpecialPermissionCompat
import top.yumik.libs.solbeanpermission.utils.PermissionChecker

internal class BindVpnServiceCompat : ISpecialPermissionCompat {

    override val targetApi: Int = AndroidVersion.ANDROID_6

    override val permission: String = Permission.BIND_VPN_SERVICE

    override fun isGranted(context: Context): Boolean {
        return VpnService.prepare(context) == null
    }

    override fun getIntent(context: Context): Intent {
        val intent = VpnService.prepare(context)
        if (PermissionChecker.checkIntentExists(context, intent)) {
            return intent
        }
        return super.getIntent(context)
    }
}