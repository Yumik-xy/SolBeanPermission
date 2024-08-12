package top.yumik.libs.solbeanpermission.permission.dangerous.v33

import android.content.Context
import top.yumik.libs.solbeanpermission.constant.AndroidVersion
import top.yumik.libs.solbeanpermission.constant.Permission
import top.yumik.libs.solbeanpermission.model.PermissionConfig
import top.yumik.libs.solbeanpermission.permission.dangerous.IDangerousPermissionCompat
import top.yumik.libs.solbeanpermission.utils.PermissionUtils

internal class ReadMediaVideoCompat : IDangerousPermissionCompat {

    override val targetApi: Int = AndroidVersion.ANDROID_13

    override val permission: String = Permission.READ_MEDIA_VIDEO

    override fun isGranted(context: Context): Boolean {
        // 在 Android 14 以上，如果用户授予了部分照片访问，那么 READ_MEDIA_VISUAL_USER_SELECTED 权限状态是授予的，而 READ_MEDIA_VIDEO 权限状态是拒绝的
        // 如果授予了全部的照片访问，那么 READ_MEDIA_VISUAL_USER_SELECTED 和 READ_MEDIA_VIDEO 都是授予的
        if (AndroidVersion.isAndroid14OrAbove()) {
            if (!PermissionUtils.checkSelfPermission(context, permission)) {
                return PermissionUtils.getPermissionCompat(Permission.READ_MEDIA_VISUAL_USER_SELECTED)
                    .isGranted(context)
            }
        }

        return PermissionUtils.checkSelfPermission(context, permission)
    }

    override fun config(context: Context): PermissionConfig {
        if (!AndroidVersion.isAndroid13OrAbove()) {
            return PermissionConfig.Replace(setOf(Permission.READ_EXTERNAL_STORAGE))
        }

        return super.config(context)
    }
}