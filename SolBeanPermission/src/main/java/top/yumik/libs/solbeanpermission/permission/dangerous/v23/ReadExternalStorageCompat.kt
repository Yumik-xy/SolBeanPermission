package top.yumik.libs.solbeanpermission.permission.dangerous.v23

import android.content.Context
import top.yumik.libs.solbeanpermission.constant.AndroidVersion
import top.yumik.libs.solbeanpermission.constant.Permission
import top.yumik.libs.solbeanpermission.model.PermissionConfig
import top.yumik.libs.solbeanpermission.permission.dangerous.IDangerousPermissionCompat

internal class ReadExternalStorageCompat : IDangerousPermissionCompat {

    override val targetApi: Int = AndroidVersion.ANDROID_6

    override val permission: String = Permission.READ_EXTERNAL_STORAGE

    override fun config(context: Context): PermissionConfig {
        if (AndroidVersion.getTargetSdkVersionCode(context) >= AndroidVersion.ANDROID_13 &&
            AndroidVersion.isAndroid13OrAbove()
        ) {
            return PermissionConfig.Replace(
                setOf(
                    Permission.READ_MEDIA_IMAGES,
                    Permission.READ_MEDIA_AUDIO,
                    Permission.READ_MEDIA_VIDEO
                )
            )
        }

        return super.config(context)
    }
}