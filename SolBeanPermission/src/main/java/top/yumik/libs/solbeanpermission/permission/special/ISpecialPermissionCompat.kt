package top.yumik.libs.solbeanpermission.permission.special

import android.app.Activity
import android.content.Context
import android.content.Intent
import top.yumik.libs.solbeanpermission.permission.IPermissionCompat
import top.yumik.libs.solbeanpermission.permission.PermissionDetailCompat

internal interface ISpecialPermissionCompat : IPermissionCompat {

    override fun isGranted(context: Context): Boolean {
        return true
    }

    override fun isNeverAsk(activity: Activity): Boolean {
        return false
    }

    override fun getIntent(context: Context): Intent {
        return PermissionDetailCompat.getIntent(context)
    }
}