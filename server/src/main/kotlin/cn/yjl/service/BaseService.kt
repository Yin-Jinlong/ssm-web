package cn.yjl.service

import org.springframework.beans.factory.annotation.Autowired
import java.security.MessageDigest

open class BaseService : Service {

    @Autowired
    lateinit var sha512: MessageDigest

    /**
     * 生成sha512字符串
     *
     * @param str 字符串
     * @return 加密后的字符串
     */
    fun genSha512String(str: String): String {
        val rs = sha512.digest(str.toByteArray())
        val s = StringBuilder()
        for (b in rs) {
            val v = b.toInt()
            s.append((v shr 4).hex)
            s.append(v.hex)
        }
        return s.toString()
    }

}

internal val HEX = charArrayOf(
    '0', '1', '2', '3', '4', '5', '6', '7', '8',
    '9', 'a', 'b', 'c', 'd', 'e', 'f'
)

internal val Int.hex
    get() = HEX[toInt() and 0xf]