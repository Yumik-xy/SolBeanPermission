package top.yumik.libs.solbeanpermission

import androidx.annotation.Keep

@Keep
interface OnPermissionInterceptor {

    /**
     * 即将请求权限，可以在此阶段进行弹窗声明
     *
     * @param permissions 即将请求的权限组
     * @param action 拦截后的操作，默认回调[OnPermissionAction.next]
     */
    fun onRequest(
        permissions: Set<String>,
        action: OnPermissionAction
    ) {
        action.next()
    }

    /**
     * 请求取消
     */
    fun onCancel() {
    }

    /**
     * 请求完成，回调结果
     *
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
    ) {

    }
}