package top.yumik.libs.solbeanpermission.permission.dangerous.v31

import android.content.Context
import top.yumik.libs.solbeanpermission.constant.AndroidVersion
import top.yumik.libs.solbeanpermission.constant.Permission
import top.yumik.libs.solbeanpermission.model.PermissionConfig
import top.yumik.libs.solbeanpermission.permission.dangerous.IDangerousPermissionCompat
import top.yumik.libs.solbeanpermission.utils.PermissionChecker
import top.yumik.libs.solbeanpermission.utils.SolBeanLog

internal class BluetoothScanCompat : IDangerousPermissionCompat {

    override val targetApi: Int = AndroidVersion.ANDROID_12

    override val permission: String = Permission.BLUETOOTH_SCAN

    override fun checkManifest(context: Context): Boolean {
        if (PermissionChecker.checkManifestPermissionNeverForLocation(context, permission)) {
            SolBeanLog.error(
                context,
                "需要在清单文件中加入 android:usesPermissionFlags=\"neverForLocation\" 属性（表示不推导设备地理位置）\n" +
                        "    <uses-permission android:name=\"android.permission.BLUETOOTH_SCAN\"\n" +
                        "        android:usesPermissionFlags=\"neverForLocation\"\n" +
                        "        tools:targetApi=\"s\" />"
            )
            return false
        }

        return super.checkManifest(context)
    }

    override fun config(context: Context): PermissionConfig {
        if (!AndroidVersion.isAndroid12OrAbove()) {
            return PermissionConfig.Replace(
                setOf(
                    Permission.ACCESS_FINE_LOCATION,
                    Permission.BLUETOOTH_ADMIN
                )
            )
        }

        return super.config(context)
    }
}