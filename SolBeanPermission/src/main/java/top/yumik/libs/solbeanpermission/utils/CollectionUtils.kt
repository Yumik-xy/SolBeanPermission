package top.yumik.libs.solbeanpermission.utils

internal fun <T> buildList(block: MutableList<T>.() -> Unit): List<T> =
    mutableListOf<T>().apply(block)

internal fun <K, V> Map<K, V>.groupBy(): Map<V, List<K>> {
    return this.entries.groupBy { entry ->
        entry.value
    }.mapValues { entry ->
        entry.value.map { it.key }
    }
}