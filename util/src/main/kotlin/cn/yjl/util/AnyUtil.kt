package cn.yjl.util

/**
 * 同步
 */
inline fun <R> Any.sync(block: () -> R) = synchronized(this, block)

/**
 * 同步（有上下文）
 */
inline fun <T, R> Any.syncWith(receiver: T, block: T.() -> R) = synchronized(this) {
    with(receiver, block)
}

