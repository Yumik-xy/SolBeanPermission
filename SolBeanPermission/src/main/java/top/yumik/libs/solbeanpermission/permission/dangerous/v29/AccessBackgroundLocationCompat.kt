package top.yumik.libs.solbeanpermission.permission.dangerous.v29

import android.content.Context
import top.yumik.libs.solbeanpermission.constant.AndroidVersion
import top.yumik.libs.solbeanpermission.constant.Permission
import top.yumik.libs.solbeanpermission.model.PermissionConfig
import top.yumik.libs.solbeanpermission.permission.dangerous.IDangerousPermissionCompat

internal class AccessBackgroundLocationCompat : IDangerousPermissionCompat {

    override val targetApi: Int = AndroidVersion.ANDROID_10

    override val permission: String = Permission.ACCESS_BACKGROUND_LOCATION

    override fun config(context: Context): PermissionConfig {
        if (AndroidVersion.isAndroid10OrAbove()) {
            return PermissionConfig.Advance(
                permissions = setOf(Permission.ACCESS_FINE_LOCATION)
            )
        }

        if (!AndroidVersion.isAndroid10OrAbove()) {
            return PermissionConfig.Replace(
                permissions = setOf(Permission.ACCESS_FINE_LOCATION)
            )
        }

        return super.config(context)
    }
}