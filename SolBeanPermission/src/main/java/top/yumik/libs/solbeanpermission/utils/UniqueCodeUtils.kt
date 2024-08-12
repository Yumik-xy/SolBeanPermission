package top.yumik.libs.solbeanpermission.utils

import java.util.concurrent.CopyOnWriteArraySet

object UniqueCodeUtils {
    
    private const val MAX_UNIQUE_CODE = 65535
    
    private val uniqueCodeSet = CopyOnWriteArraySet<Int>()
    
    private var uniqueCode = 0
    
    fun getUniqueCode(): Int {
        while (uniqueCode in uniqueCodeSet) {
            uniqueCode = (uniqueCode + 1) % MAX_UNIQUE_CODE
        }
        uniqueCodeSet.add(uniqueCode)
        return uniqueCode
    }
    
    fun removeUniqueCode(code: Int) {
        uniqueCodeSet.remove(code)
    }
    
    fun clear() {
        uniqueCodeSet.clear()
    }
}