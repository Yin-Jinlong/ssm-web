package cn.yjl.security.token

import cn.yjl.util.log.getLogger
import cn.yjl.util.now
import com.google.gson.Gson
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 *
 * @author YJL
 */
object TokenUtil {

    val LOGGER = getLogger()

    lateinit var tokenKey: TokenKey

    /**
     * 这里使用默认（全局的用于响应返回）
     */
    val gson = Gson()

    fun init(key: TokenKey) {
        tokenKey = key
    }

    fun <T> encode(t: Token<T>): String {
        return tokenKey.encode(gson.toJson(t))
    }

    inline fun <reified T> decode(base64: String): Token<T>? {
        kotlin.runCatching {
            return gson.fromJson(
                tokenKey.decode(base64),
                TokenClassType(Token::class.java, T::class.java)
            )
        }.onFailure {
            LOGGER.info("bad token: $base64")
        }
        return null
    }

}

class TokenClassType(
    val thisType: Type,
    vararg val argsType: Type
) : ParameterizedType {

    override fun getActualTypeArguments(): Array<out Type> = argsType

    override fun getRawType(): Type = thisType

    override fun getOwnerType(): Type? = null
}

fun Token<Int>.isAlive() = now() < time()