package cn.yjl.util

inline fun Any.sync(block: () -> Unit) {
    synchronized(this) {
        block()
    }
}

inline fun Any?.isNull() = this == null

inline fun Any?.isNotNull() = this != null