package top.yumik.libs.solbeanpermission.permission.dangerous.v33

import android.app.Activity
import android.content.Context
import top.yumik.libs.solbeanpermission.constant.AndroidVersion
import top.yumik.libs.solbeanpermission.constant.Permission
import top.yumik.libs.solbeanpermission.model.PermissionConfig
import top.yumik.libs.solbeanpermission.permission.dangerous.IDangerousPermissionCompat
import top.yumik.libs.solbeanpermission.utils.PermissionUtils

internal class BodySensorsBackgroundCompat : IDangerousPermissionCompat {

    override val targetApi: Int = AndroidVersion.ANDROID_13

    override val permission: String = Permission.BODY_SENSORS_BACKGROUND

    private val bodySensorsCompat by lazy {
        PermissionUtils.getPermissionCompat(Permission.BODY_SENSORS)
    }

    override fun isGranted(context: Context): Boolean {
        return bodySensorsCompat.isGranted(context) &&
                super.isGranted(context)
    }

    override fun isNeverAsk(activity: Activity): Boolean {
        return bodySensorsCompat.isNeverAsk(activity) || super.isNeverAsk(activity)
    }

    override fun config(context: Context): PermissionConfig {
        if (!AndroidVersion.isAndroid13OrAbove()) {
            return PermissionConfig.Replace(
                permissions = setOf(Permission.BODY_SENSORS)
            )
        }

        return PermissionConfig.Advance(
            permissions = setOf(Permission.BODY_SENSORS)
        )
    }
}