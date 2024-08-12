package top.yumik.libs.solbeanpermission.permission.dangerous.v28

import top.yumik.libs.solbeanpermission.constant.AndroidVersion
import top.yumik.libs.solbeanpermission.constant.Permission
import top.yumik.libs.solbeanpermission.permission.dangerous.IDangerousPermissionCompat

internal class AccessHandoverCompat : IDangerousPermissionCompat {

    override val targetApi: Int = AndroidVersion.ANDROID_9

    override val permission: String = Permission.ACCEPT_HANDOVER
}