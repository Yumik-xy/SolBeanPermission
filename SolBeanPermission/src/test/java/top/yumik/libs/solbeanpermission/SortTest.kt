package top.yumik.libs.solbeanpermission

import org.junit.Test

class SortTest {

    @Test
    fun sortTest() {
        val numberList = mutableListOf<Int>()
        repeat(10) {
            numberList.add((0..100).random())
        }
        println("排序前：$numberList")
        val sortedList = numberList.sortedBy {
            if (isOdd(it)) {
                2
            } else {
                1
            }

        }
        println("排序后：$sortedList")
    }

    private fun isOdd(num: Int): Boolean {
        return num % 2 == 1
    }
}