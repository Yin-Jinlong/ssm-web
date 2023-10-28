package cn.yjl.security.token

import cn.yjl.util.now
import java.util.*

/**
 *
 * @author YJL
 */
open class Token<T>(
    val v: T,
    alive: Long = TokenUtil.DefaultAliveTime
) {

    companion object {

        fun timeLong2Str(long: Long): String {
            return java.lang.Long.toString(long, Character.MAX_RADIX)
        }

        fun timeLongFromStr(time: String): Long {
            return java.lang.Long.parseLong(time, Character.MAX_RADIX)
        }
    }

    /**
     * 过期时间字符串
     */
    val t: String = timeLong2Str(now() + alive)

    /**
     * 过期时间戳
     */
    fun time() = timeLongFromStr(t)

    override fun toString(): String {
        return "Token(value=$v, time=${time()})"
    }

    override fun hashCode(): Int = Objects.hash(v, t)

    override operator fun equals(other: Any?): Boolean {
        return if (other !is Token<*>) false
        else v == other.v && t == other.t
    }
}

