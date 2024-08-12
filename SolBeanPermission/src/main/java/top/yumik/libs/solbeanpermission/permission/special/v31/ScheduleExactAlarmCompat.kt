package top.yumik.libs.solbeanpermission.permission.special.v31

import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.core.content.getSystemService
import top.yumik.libs.solbeanpermission.constant.AndroidVersion
import top.yumik.libs.solbeanpermission.constant.Permission
import top.yumik.libs.solbeanpermission.permission.special.ISpecialPermissionCompat
import top.yumik.libs.solbeanpermission.utils.PermissionChecker
import top.yumik.libs.solbeanpermission.utils.PermissionUtils

internal class ScheduleExactAlarmCompat : ISpecialPermissionCompat {

    override val targetApi: Int = AndroidVersion.ANDROID_12

    override val permission: String = Permission.SCHEDULE_EXACT_ALARM

    override fun isGranted(context: Context): Boolean {
        if (AndroidVersion.isAndroid12OrAbove()) {
            return context.getSystemService<AlarmManager>()?.canScheduleExactAlarms()
                ?: super.isGranted(context)
        }

        return super.isGranted(context)
    }

    override fun getIntent(context: Context): Intent {
        if (AndroidVersion.isAndroid12OrAbove()) {
            val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
            intent.setData(PermissionUtils.getPackageUri(context))
            if (PermissionChecker.checkIntentExists(context, intent)) {
                return intent
            }
        }

        return super.getIntent(context)
    }
}