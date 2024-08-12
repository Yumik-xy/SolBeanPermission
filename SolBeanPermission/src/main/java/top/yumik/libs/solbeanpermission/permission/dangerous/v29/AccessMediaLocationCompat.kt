package top.yumik.libs.solbeanpermission.permission.dangerous.v29

import android.content.Context
import top.yumik.libs.solbeanpermission.constant.AndroidVersion
import top.yumik.libs.solbeanpermission.constant.Permission
import top.yumik.libs.solbeanpermission.model.PermissionConfig
import top.yumik.libs.solbeanpermission.permission.dangerous.IDangerousPermissionCompat
import top.yumik.libs.solbeanpermission.utils.PermissionChecker

internal class AccessMediaLocationCompat : IDangerousPermissionCompat {

    override val targetApi: Int = AndroidVersion.ANDROID_10

    override val permission: String = Permission.ACCESS_MEDIA_LOCATION

    override fun config(context: Context): PermissionConfig {
        if (!AndroidVersion.isAndroid10OrAbove()) {
            return PermissionConfig.Replace(setOf(Permission.READ_EXTERNAL_STORAGE))
        }

        when {
            // 适配了分区存储，如果项目 targetSdkVersion <= 32 需要申请 READ_EXTERNAL_STORAGE
            !PermissionChecker.checkManifestRequestLegacyExternalStorage(context) &&
                    AndroidVersion.getTargetSdkVersionCode(context) <= AndroidVersion.ANDROID_12_L -> {
                return PermissionConfig.Advance(setOf(Permission.READ_EXTERNAL_STORAGE))
            }

            // 适配了分区存储，如果项目 targetSdkVersion >= 33 需要申请 READ_MEDIA_IMAGES
            !PermissionChecker.checkManifestRequestLegacyExternalStorage(context) &&
                    AndroidVersion.getTargetSdkVersionCode(context) >= AndroidVersion.ANDROID_13 -> {
                return PermissionConfig.Advance(setOf(Permission.READ_MEDIA_IMAGES))
            }

            // 没有适配了分区存储，如果项目 targetSdkVersion <= 29 需要申请 READ_EXTERNAL_STORAGE
            PermissionChecker.checkManifestRequestLegacyExternalStorage(context) &&
                    AndroidVersion.getTargetSdkVersionCode(context) <= AndroidVersion.ANDROID_10 -> {
                return PermissionConfig.Advance(setOf(Permission.READ_EXTERNAL_STORAGE))
            }

            // 没有适配了分区存储，如果项目 targetSdkVersion >= 30 需要申请 MANAGE_EXTERNAL_STORAGE
            PermissionChecker.checkManifestRequestLegacyExternalStorage(context) &&
                    AndroidVersion.getTargetSdkVersionCode(context) >= AndroidVersion.ANDROID_11 -> {
                return PermissionConfig.Advance(setOf(Permission.MANAGE_EXTERNAL_STORAGE))
            }
        }

        return super.config(context)
    }
}