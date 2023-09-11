package cn.yjl.util

inline fun Any.sync(block: () -> Unit) {
    synchronized(this) {
        block()
    }
}