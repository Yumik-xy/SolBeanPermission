package top.yumik.libs.solbeanpermission.permission.dangerous.v31

import android.content.Context
import top.yumik.libs.solbeanpermission.constant.AndroidVersion
import top.yumik.libs.solbeanpermission.constant.Permission
import top.yumik.libs.solbeanpermission.model.PermissionConfig
import top.yumik.libs.solbeanpermission.permission.dangerous.IDangerousPermissionCompat

internal class BluetoothConnectCompat : IDangerousPermissionCompat {

    override val targetApi: Int = AndroidVersion.ANDROID_12

    override val permission: String = Permission.BLUETOOTH_CONNECT

    override fun config(context: Context): PermissionConfig {
        if (!AndroidVersion.isAndroid12OrAbove()) {
            return PermissionConfig.Replace(setOf(Permission.BLUETOOTH))
        }

        return super.config(context)
    }
}