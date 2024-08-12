package top.yumik.libs.solbeanpermission

import org.junit.Test
import top.yumik.libs.solbeanpermission.utils.PermissionCompat

class PermissionTest {

    @Test
    fun getSpecialPermissionsTest() {
        assert(PermissionCompat.specialPermissionCompat.isNotEmpty())
        PermissionCompat.specialPermissionCompat.forEach {
            println(it.permission)
        }
    }

    @Test
    fun getDangerousPermissionsTest() {
        assert(PermissionCompat.dangerousPermissionCompat.isNotEmpty())
        PermissionCompat.dangerousPermissionCompat.forEach {
            println(it.permission)
        }
    }

    @Test
    fun getAllPermissionsTest() {
        assert(PermissionCompat.allPermissionCompat.isNotEmpty())
        PermissionCompat.allPermissionCompat.forEach {
            println(it.permission)
        }
    }

}