package top.yumik.libs.solbeanpermission.model

internal sealed class PermissionConfig(
    open val permissions: Set<String>
) {

    data object None : PermissionConfig(emptySet())

    data class Replace(
        override val permissions: Set<String> = emptySet()
    ) : PermissionConfig(permissions)

    data class Advance(
        override val permissions: Set<String> = emptySet()
    ) : PermissionConfig(permissions)
}
