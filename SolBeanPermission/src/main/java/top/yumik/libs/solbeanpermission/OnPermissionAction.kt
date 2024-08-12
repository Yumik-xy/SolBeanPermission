package top.yumik.libs.solbeanpermission

import androidx.annotation.Keep

@Keep
interface OnPermissionAction {

    /**
     * 取消后续的权限组请求
     */
    fun cancel()

    /**
     * 继续后续的权限组请求
     */
    fun next()

    /**
     * 跳过当前的权限组请求
     */
    fun skip()
}