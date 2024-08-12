package top.yumik.libs.solbeanpermission.utils

object StringUtils {

    fun String.cutMiddle(start: Char, end: Char, isContain: Boolean = false): String {
        val startIndex = this.indexOf(start)
        val endIndex = this.indexOf(end, startIndex)
        if (startIndex == -1 || endIndex == -1) {
            return ""
        }
        return if (isContain) {
            this.substring(startIndex, endIndex + 1)
        } else {
            this.substring(startIndex + 1, endIndex)
        }
    }
}