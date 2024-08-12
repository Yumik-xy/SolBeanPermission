package top.yumik.libs.solbeanpermission.permission.dangerous.v33

import android.content.Context
import top.yumik.libs.solbeanpermission.constant.AndroidVersion
import top.yumik.libs.solbeanpermission.constant.Permission
import top.yumik.libs.solbeanpermission.model.PermissionConfig
import top.yumik.libs.solbeanpermission.permission.dangerous.IDangerousPermissionCompat

internal class PostNotificationsCompat : IDangerousPermissionCompat {

    override val targetApi: Int = AndroidVersion.ANDROID_13

    override val permission: String = Permission.POST_NOTIFICATIONS

    override fun config(context: Context): PermissionConfig {
        if (!AndroidVersion.isAndroid13OrAbove()) {
            return PermissionConfig.Replace(setOf(Permission.NOTIFICATION_SERVICE))
        }

        return super.config(context)
    }
}