package top.yumik.libs.solbeanpermission.permission.dangerous

import android.app.Activity
import android.content.Context
import android.content.Intent
import top.yumik.libs.solbeanpermission.constant.AndroidVersion
import top.yumik.libs.solbeanpermission.model.PermissionConfig
import top.yumik.libs.solbeanpermission.permission.IPermissionCompat
import top.yumik.libs.solbeanpermission.permission.PermissionDetailCompat
import top.yumik.libs.solbeanpermission.utils.PermissionUtils

internal interface IDangerousPermissionCompat : IPermissionCompat {

    override fun isGranted(context: Context): Boolean {
        return PermissionUtils.checkSelfPermission(context, permission)
    }

    override fun isNeverAsk(activity: Activity): Boolean {
        return !isGranted(activity) &&
                !PermissionUtils.shouldShowRequestPermissionRationale(activity, permission)
    }

    override fun getIntent(context: Context): Intent {
        return PermissionDetailCompat.getIntent(context)
    }

    override fun config(context: Context): PermissionConfig {
        if (AndroidVersion.getAndroidVersionCode() < targetApi) {
            return PermissionConfig.Replace()
        }

        return super.config(context)
    }
}