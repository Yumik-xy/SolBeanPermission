package top.yumik.libs.solbeanpermission.permission.dangerous.v23

import android.app.Activity
import android.content.Context
import top.yumik.libs.solbeanpermission.constant.AndroidVersion
import top.yumik.libs.solbeanpermission.constant.Permission
import top.yumik.libs.solbeanpermission.permission.dangerous.IDangerousPermissionCompat

internal class WriteExternalStorageCompat : IDangerousPermissionCompat {

    override val targetApi: Int = AndroidVersion.ANDROID_6

    override val permission: String = Permission.WRITE_EXTERNAL_STORAGE

    override fun isGranted(context: Context): Boolean {
        // 适配 Android 13 ，高于这个版本不能申请 WRITE_EXTERNAL_STORAGE 权限，系统会直接拒绝而不会弹出授权对话框
        if (AndroidVersion.isAndroid13OrAbove() &&
            AndroidVersion.getTargetSdkVersionCode(context) >= AndroidVersion.ANDROID_13
        ) {
            return false
        }

        return super.isGranted(context)
    }

    override fun isNeverAsk(activity: Activity): Boolean {
        // 适配 Android 13 ，高于这个版本不能申请 WRITE_EXTERNAL_STORAGE 权限，系统会直接拒绝而不会弹出授权对话框
        if (AndroidVersion.isAndroid13OrAbove()
            && AndroidVersion.getTargetSdkVersionCode(activity) >= AndroidVersion.ANDROID_13
        ) {
            return true
        }

        return super.isNeverAsk(activity)
    }
}