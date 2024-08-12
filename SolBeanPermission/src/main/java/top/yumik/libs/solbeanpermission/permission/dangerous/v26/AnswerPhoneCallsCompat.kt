package top.yumik.libs.solbeanpermission.permission.dangerous.v26

import android.app.Activity
import android.content.Context
import top.yumik.libs.solbeanpermission.constant.AndroidVersion
import top.yumik.libs.solbeanpermission.constant.Permission
import top.yumik.libs.solbeanpermission.permission.dangerous.IDangerousPermissionCompat

internal class AnswerPhoneCallsCompat : IDangerousPermissionCompat {

    override val targetApi: Int = AndroidVersion.ANDROID_8

    override val permission: String = Permission.ANSWER_PHONE_CALLS

    override fun isGranted(context: Context): Boolean {
        if (!AndroidVersion.isAndroid8OrAbove()) {
            return true
        }

        return super.isGranted(context)
    }

    override fun isNeverAsk(activity: Activity): Boolean {
        if (!AndroidVersion.isAndroid8OrAbove()) {
            return false
        }

        return super.isNeverAsk(activity)
    }
}