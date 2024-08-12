package top.yumik.libs.solbeanpermission

import androidx.annotation.Keep

@Keep
fun interface OnPermissionCallback {

    /**
     * @param permissions 所有请求的权限
     * @param granted 同意的权限组
     * @param denied 拒绝的权限组
     * @param neverAsk 不再询问的权限组
     * @param checkFail 权限自检失败的权限组
     */
    fun onResult(
        permissions: Set<String>,
        granted: Set<String>,
        denied: Set<String>,
        neverAsk: Set<String>,
        checkFail: Set<String>
    )
}