package top.yumik.libs.solbeanpermission.permission.special.v23

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.provider.Settings
import top.yumik.libs.solbeanpermission.constant.AndroidVersion
import top.yumik.libs.solbeanpermission.constant.Permission
import top.yumik.libs.solbeanpermission.permission.special.ISpecialPermissionCompat
import top.yumik.libs.solbeanpermission.utils.AndroidManifestParseUtils
import top.yumik.libs.solbeanpermission.utils.PermissionChecker

internal class BindNotificationListenerServiceCompat : ISpecialPermissionCompat {

    companion object {

        // Settings.Secure.ENABLED_NOTIFICATION_LISTENERS
        private const val SETTING_ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners"
    }

    override val targetApi: Int = AndroidVersion.ANDROID_6

    override val permission = Permission.BIND_NOTIFICATION_LISTENER_SERVICE

    override fun isGranted(context: Context): Boolean {
        // 系统支持通知监听服务
        val enableNotificationListener = Settings.Secure.getString(
            context.contentResolver, SETTING_ENABLED_NOTIFICATION_LISTENERS
        )
        if (enableNotificationListener.isNullOrEmpty()) {
            return false
        }

        // 读取到的 enabled_notification_listeners 以冒号分隔，参考数据如下：
        // com.google.android.as/com.google.android.apps.miphone.aiai.common.notification.service.AiAiNotificationListenerService:com.google.android.apps.nexuslauncher/com.android.launcher3.notification.NotificationListener
        return enableNotificationListener.split(":").mapNotNull {
            ComponentName.unflattenFromString(it)
        }.filter { componentName ->
            // 1. 检查包名是否一致
            componentName.packageName == context.packageName
        }.any { componentName ->
            // 2. 检查对应的类是否存在
            PermissionChecker.checkClassExists(componentName.className)
        }
    }

    override fun getIntent(context: Context): Intent {
        if (AndroidVersion.isAndroid11OrAbove()) {
            val androidManifestInfo = AndroidManifestParseUtils.parse(context)
            // 这里获取只一个服务信息，如果不存在或存在多个则返回 null
            val onlyServiceInfo = androidManifestInfo.services.filter {
                it.permission == permission
            }.takeIf {
                it.size == 1
            }?.first()

            if (onlyServiceInfo != null) {
                val intent = Intent(Settings.ACTION_NOTIFICATION_LISTENER_DETAIL_SETTINGS)
                intent.putExtra(
                    Settings.EXTRA_NOTIFICATION_LISTENER_COMPONENT_NAME,
                    ComponentName(context, onlyServiceInfo.name).flattenToString()
                )
                if (PermissionChecker.checkIntentExists(context, intent)) {
                    return intent
                }
            }
        }

        val intent = Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
        if (PermissionChecker.checkIntentExists(context, intent)) {
            return intent
        }

        return super.getIntent(context)
    }
}