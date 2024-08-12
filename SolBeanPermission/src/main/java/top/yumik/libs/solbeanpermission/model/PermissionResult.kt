package top.yumik.libs.solbeanpermission.model

enum class PermissionResult {
    // 临时处理
    Idle, Requesting, Advance, Downgrade,

    // 实际结果
    Granted, Denied, NeverAsk, CheckFail
}