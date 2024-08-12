package top.yumik.libs.solbeanpermission.permission.dangerous.v33

import android.content.Context
import top.yumik.libs.solbeanpermission.constant.AndroidVersion
import top.yumik.libs.solbeanpermission.constant.Permission
import top.yumik.libs.solbeanpermission.model.PermissionConfig
import top.yumik.libs.solbeanpermission.permission.dangerous.IDangerousPermissionCompat
import top.yumik.libs.solbeanpermission.utils.PermissionChecker
import top.yumik.libs.solbeanpermission.utils.SolBeanLog

internal class NearbyWifiDevicesCompat : IDangerousPermissionCompat {

    override val targetApi: Int = AndroidVersion.ANDROID_13

    override val permission: String = Permission.NEARBY_WIFI_DEVICES

    override fun checkManifest(context: Context): Boolean {
        if (PermissionChecker.checkManifestPermissionNeverForLocation(context, permission)) {
            SolBeanLog.error(
                context,
                "需要在清单文件中加入 android:usesPermissionFlags=\"neverForLocation\" 属性（表示不推导设备地理位置）\n" +
                        "    <uses-permission android:name=\"android.permission.NEARBY_WIFI_DEVICES\"\n" +
                        "        android:usesPermissionFlags=\"neverForLocation\"\n" +
                        "        tools:targetApi=\"s\" />"
            )
            return false
        }

        return super.checkManifest(context)
    }

    override fun config(context: Context): PermissionConfig {
        if (!AndroidVersion.isAndroid13OrAbove()) {
            return PermissionConfig.Replace(setOf(Permission.ACCESS_FINE_LOCATION))
        }

        return super.config(context)
    }
}