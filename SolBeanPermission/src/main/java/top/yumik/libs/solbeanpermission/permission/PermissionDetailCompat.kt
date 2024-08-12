package top.yumik.libs.solbeanpermission.permission

import android.content.Context
import android.content.Intent
import android.provider.Settings
import top.yumik.libs.solbeanpermission.utils.PermissionChecker
import top.yumik.libs.solbeanpermission.utils.PermissionUtils


internal object PermissionDetailCompat {
    
    fun getIntent(context: Context): Intent {
        var intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = PermissionUtils.getPackageUri(context)
        if (PermissionChecker.checkIntentExists(context, intent)) {
            return intent
        }
        
        intent = Intent(Settings.ACTION_APPLICATION_SETTINGS)
        if (PermissionChecker.checkIntentExists(context, intent)) {
            return intent
        }
        
        intent = Intent(Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS)
        if (PermissionChecker.checkIntentExists(context, intent)) {
            return intent
        }
        
        return Intent(Settings.ACTION_SETTINGS)
    }
}