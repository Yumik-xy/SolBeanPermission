package top.yumik.libs.solbeanpermission.permission.dangerous.v23

import android.content.Context
import top.yumik.libs.solbeanpermission.constant.AndroidVersion
import top.yumik.libs.solbeanpermission.constant.Permission
import top.yumik.libs.solbeanpermission.model.PermissionConfig
import top.yumik.libs.solbeanpermission.permission.dangerous.IDangerousPermissionCompat

internal class BluetoothAdminCompat : IDangerousPermissionCompat {

    override val targetApi: Int = AndroidVersion.ANDROID_6

    override val permission: String = Permission.BLUETOOTH_ADMIN

    override fun config(context: Context): PermissionConfig {
        return PermissionConfig.Advance(setOf(Permission.BLUETOOTH))
    }
}