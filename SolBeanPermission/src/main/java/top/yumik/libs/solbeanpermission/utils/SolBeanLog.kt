package top.yumik.libs.solbeanpermission.utils

import android.content.Context
import android.util.Log

internal object SolBeanLog {

    private const val TAG = "SolBeanPermission"

    @JvmStatic
    fun info(message: String) {
        message.split("\n").forEach {
            Log.i(TAG, buildMessage(it))
        }
    }

    @JvmStatic
    fun warn(message: String) {
        message.split("\n").forEach {
            Log.w(TAG, buildMessage(it))
        }
    }

    @JvmStatic
    fun error(context: Context, message: String) {
        message.split("\n").forEach {
            Log.e(TAG, buildMessage(it))
        }
        if (PermissionUtils.isDebugMode(context)) {
            throw IllegalStateException(message)
        }
    }

    @JvmStatic
    @Suppress("NOTHING_TO_INLINE")
    private inline fun buildMessage(it: String?) = "[SBP] $it"

}