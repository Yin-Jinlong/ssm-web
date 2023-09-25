package cn.aynu.yjl.tools

import java.io.File

val SIZE_UNITS = arrayOf("B", "KB", "MB", "GB", "TB", "PB")

val File.size: String
    get() {
        var i = 0
        var s = length().toDouble()
        while (s > 1024) {
            s /= 1024
            i++
        }
        return "${(s + "000")..6} ${SIZE_UNITS[i]}"
    }

operator fun CharSequence.rangeTo(endIndex: Int): String = substring(0, endIndex)

val Any.str
    get() = toString()

operator fun Any.plus(s: Any): String = str + s.str