package top.yumik.libs.solbeanpermission.permission

import android.app.Activity
import android.content.Context
import android.content.Intent
import top.yumik.libs.solbeanpermission.constant.AndroidVersion

class UnSupportPermissionCompatImp(override val permission: String) : IPermissionCompat {

    override val targetApi: Int = AndroidVersion.ANDROID_1

    override fun isGranted(context: Context): Boolean {
        return false
    }

    override fun isNeverAsk(activity: Activity): Boolean {
        return true
    }

    override fun getIntent(context: Context): Intent {
        return PermissionDetailCompat.getIntent(context)
    }
}