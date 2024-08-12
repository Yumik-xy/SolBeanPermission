package top.yumik.libs.solbeanpermission.permission.dangerous.v26

import android.content.Context
import top.yumik.libs.solbeanpermission.constant.AndroidVersion
import top.yumik.libs.solbeanpermission.constant.Permission
import top.yumik.libs.solbeanpermission.model.PermissionConfig
import top.yumik.libs.solbeanpermission.permission.dangerous.IDangerousPermissionCompat

internal class ReadPhoneNumbersCompat : IDangerousPermissionCompat {

    override val targetApi: Int = AndroidVersion.ANDROID_8

    override val permission: String = Permission.READ_PHONE_NUMBERS

    override fun config(context: Context): PermissionConfig {
        if (!AndroidVersion.isAndroid8OrAbove()) {
            return PermissionConfig.Replace(
                permissions = setOf(Permission.READ_PHONE_STATE)
            )
        }

        return super.config(context)
    }
}