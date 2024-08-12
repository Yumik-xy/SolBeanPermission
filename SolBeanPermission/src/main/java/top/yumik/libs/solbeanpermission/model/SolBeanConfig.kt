package top.yumik.libs.solbeanpermission.model

data class SolBeanConfig(

    /**
     * 强制标识为`debug`模式
     *
     * 该模式下会检查权限问题，并直接抛出异常，默认不开启
     */
    var debuggable: Boolean = false
)
