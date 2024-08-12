package top.yumik.libs.solbeanpermission.permission.dangerous.v29

import top.yumik.libs.solbeanpermission.constant.AndroidVersion
import top.yumik.libs.solbeanpermission.constant.Permission
import top.yumik.libs.solbeanpermission.permission.dangerous.IDangerousPermissionCompat

internal class ActivityRecognitionCompat : IDangerousPermissionCompat {

    override val targetApi: Int = AndroidVersion.ANDROID_10

    override val permission: String = Permission.ACTIVITY_RECOGNITION
}