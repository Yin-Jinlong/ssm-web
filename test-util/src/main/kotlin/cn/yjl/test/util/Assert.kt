package cn.yjl.test.util

typealias Target = Any?
typealias Value = Any?

infix fun Value.sameAs(v: Target) {
    if (this == v)
        return
    throw AssertionError("$this != $v")
}

infix fun Value.oneOf(v: List<Target>) {
    if (v.contains(this))
        return
    throw AssertionError("$this not in $v")
}

inline fun same(v: Value, t: Target, msg: String) {
    same(v, t) { msg }
}

inline fun same(v: Value, t: Target, msg: Throwable) {
    same(v, t) { msg }
}

fun same(v: Value, t: Target, msg: () -> Any?) {
    if (v == t)
        return
    throw AssertionError(msg())
}