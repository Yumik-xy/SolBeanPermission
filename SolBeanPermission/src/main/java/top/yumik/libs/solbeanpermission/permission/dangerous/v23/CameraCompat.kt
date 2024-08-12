package top.yumik.libs.solbeanpermission.permission.dangerous.v23

import top.yumik.libs.solbeanpermission.constant.AndroidVersion
import top.yumik.libs.solbeanpermission.constant.Permission
import top.yumik.libs.solbeanpermission.permission.dangerous.IDangerousPermissionCompat

internal class CameraCompat : IDangerousPermissionCompat {

    override val targetApi: Int = AndroidVersion.ANDROID_6

    override val permission: String = Permission.CAMERA
}