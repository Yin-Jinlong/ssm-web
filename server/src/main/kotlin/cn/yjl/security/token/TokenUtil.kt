package cn.yjl.security.token

import cn.yjl.log.util.getLogger
import cn.yjl.util.now
import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

/**
 *
 * @author YJL
 */
@Component
@OptIn(ExperimentalEncodingApi::class)
class TokenUtil {

    val LOGGER = getLogger()

    @Autowired
    lateinit var tokenKey: TokenKey

    /**
     * 这里使用默认（全局的用于响应返回）
     */
    val gson = Gson()

    fun <T> encode(t: Token<T>): String {
        val json = gson.toJson(t)
        val r = tokenKey.encode(json.toByteArray())
        return Base64.encode(r, 0, r.size)
    }

    final inline fun <reified T> decode(base64: String): Token<T>? {
        kotlin.runCatching {
            return gson.fromJson(
                tokenKey.decode(Base64.decode(base64)).decodeToString(),
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