package top.yumik.libs.solbeanpermission.sample

import android.os.Build
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import top.yumik.libs.solbeanpermission.utils.toast

class TestNotificationListenerService : NotificationListenerService() {

    override fun onListenerConnected() {
        super.onListenerConnected()
        activeNotifications.forEach {
            removeNotification(it)
        }
        toast("onListenerConnected")
    }

    override fun onListenerDisconnected() {
        super.onListenerDisconnected()
        toast("onListenerDisconnected")
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)
        if (sbn == null) {
            return
        }

        removeNotification(sbn)
    }

    private fun removeNotification(sbn: StatusBarNotification) {
        if (sbn.isClearable) {
            cancelNotification(sbn.key)
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                snoozeNotification(sbn.key, Long.MAX_VALUE - System.currentTimeMillis())
            }
        }
    }
}