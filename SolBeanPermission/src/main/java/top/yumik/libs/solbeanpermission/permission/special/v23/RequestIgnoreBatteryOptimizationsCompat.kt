package top.yumik.libs.solbeanpermission.permission.special.v23

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.PowerManager
import android.provider.Settings
import androidx.core.content.getSystemService
import top.yumik.libs.solbeanpermission.constant.AndroidVersion
import top.yumik.libs.solbeanpermission.constant.Permission
import top.yumik.libs.solbeanpermission.permission.special.ISpecialPermissionCompat
import top.yumik.libs.solbeanpermission.utils.PermissionChecker
import top.yumik.libs.solbeanpermission.utils.PermissionUtils

@SuppressLint("BatteryLife")
internal class RequestIgnoreBatteryOptimizationsCompat : ISpecialPermissionCompat {

    override val targetApi: Int = AndroidVersion.ANDROID_6

    override val permission: String = Permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS

    override fun isGranted(context: Context): Boolean {
        return context.getSystemService<PowerManager>()
            ?.isIgnoringBatteryOptimizations(context.packageName)
            ?: super.isGranted(context)
    }

    override fun getIntent(context: Context): Intent {
        var intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
        intent.setData(PermissionUtils.getPackageUri(context))
        if (PermissionChecker.checkIntentExists(context, intent)) {
            return intent
        }

        intent = Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS)
        if (PermissionChecker.checkIntentExists(context, intent)) {
            return intent
        }

        return super.getIntent(context)
    }
}