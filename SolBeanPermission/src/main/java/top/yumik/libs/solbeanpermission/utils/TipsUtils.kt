package top.yumik.libs.solbeanpermission.utils

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

private val mainHandler = Handler(Looper.getMainLooper())

fun Context.toast(message: String) {
    mainHandler.post {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

fun Context.toast(@StringRes message: Int) {
    toast(getString(message))
}

fun Fragment.toast(message: String) {
    requireContext().toast(message)
}

fun Fragment.toast(@StringRes message: Int) {
    requireContext().toast(message)
}