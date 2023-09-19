package cn.yjl.util


class ArrayUtil {
    companion object{
        operator fun ByteArray.get(range: IntRange): ByteArray {
            return copyOfRange(range.first, range.last + 1)
        }

        operator fun ByteArray.get(range: UIntRange): ByteArray {
            val bytes = ByteArray((range.last - range.first).toInt())
            for ((ri, oi) in (range.first.toInt() until range.last.toInt()).withIndex()) {
                bytes[ri] = this[oi]
            }
            return bytes
        }
    }
}
