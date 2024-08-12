package top.yumik.libs.solbeanpermission.model

import android.content.pm.PackageInfo
import top.yumik.libs.solbeanpermission.constant.AndroidVersion

/**
 * Android Manifests 解析的数据类
 */
data class AndroidManifestInfo(
    var packageName: String = "",
    var userSdk: UserSdkInfo? = null,
    val usesPermissions: MutableList<UsesPermissionInfo> = mutableListOf(),
    var application: ApplicationInfo? = null,
    val activities: MutableList<ActivityInfo> = mutableListOf(),
    val services: MutableList<ServiceInfo> = mutableListOf()
) {

    data class UserSdkInfo(
        val minSdkVersion: Int,
        val targetSdkVersion: Int
    )

    data class UsesPermissionInfo(
        val name: String,
        val maxSdkVersion: Int,
        val userPermissionFlags: Int
    ) {

        companion object {
            private const val ANDROID_12_REQUESTED_PERMISSION_NEVER_FOR_LOCATION = 0x00010000

            val FLAG_REQUESTED_PERMISSION_NEVER_FOR_LOCATION =
                if (AndroidVersion.isAndroid12OrAbove()) {
                    PackageInfo.REQUESTED_PERMISSION_NEVER_FOR_LOCATION
                } else {
                    ANDROID_12_REQUESTED_PERMISSION_NEVER_FOR_LOCATION
                }
        }

        val isNeverForLocation: Boolean
            get() = (userPermissionFlags and FLAG_REQUESTED_PERMISSION_NEVER_FOR_LOCATION) != 0
    }

    data class ApplicationInfo(
        val name: String?,
        val requestLegacyExternalStorage: Boolean
    )

    data class ActivityInfo(
        val name: String,
        val supportsPictureInPicture: Boolean
    )

    data class ServiceInfo(
        val name: String,
        val permission: String
    )
}