package top.yumik.libs.solbeanpermission

import android.content.Context
import android.content.Intent
import androidx.annotation.Keep
import androidx.fragment.app.FragmentActivity
import top.yumik.libs.solbeanpermission.model.SolBeanConfig
import top.yumik.libs.solbeanpermission.permission.PermissionDetailCompat
import top.yumik.libs.solbeanpermission.ui.PermissionFragment
import top.yumik.libs.solbeanpermission.utils.PermissionUtils

@Keep
object SolBeanPermission {

    internal val config: SolBeanConfig = SolBeanConfig()

    @JvmStatic
    @JvmOverloads
    fun requestPermissions(
        activity: FragmentActivity,
        permissions: Set<String>,
        callback: OnPermissionCallback,
        interceptor: OnPermissionInterceptor? = null
    ) {
        PermissionFragment.launch(activity, permissions, callback, interceptor)
    }

    @JvmStatic
    @JvmOverloads
    fun requestPermissions(
        activity: FragmentActivity,
        permission: String,
        callback: OnPermissionCallback,
        interceptor: OnPermissionInterceptor? = null
    ) {
        requestPermissions(activity, setOf(permission), callback, interceptor)
    }

    @JvmStatic
    fun checkPermissions(
        context: Context,
        permissions: Set<String>
    ): Set<String> {
        return permissions.filter { permission ->
            checkPermission(context, permission)
        }.toSet()
    }

    @JvmStatic
    fun checkPermission(
        context: Context,
        permission: String
    ): Boolean {
        return PermissionUtils.getPermissionCompat(permission).isGranted(context)
    }

    @JvmStatic
    fun config(action: SolBeanConfig.() -> Unit) {
        config.action()
    }

    @JvmStatic
    fun getPermissionDetailIntent(context: Context): Intent {
        return PermissionDetailCompat.getIntent(context)
    }
}