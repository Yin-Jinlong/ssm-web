package cn.yjl.security.token

import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.Key
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.PBEParameterSpec
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

/**
 * Token密码
 *
 * 附带Cipher实例
 * @author YJL
 */
@Component
@OptIn(ExperimentalEncodingApi::class)
class TokenKey {

    lateinit var key: Key

    @Value("\${token-pwd}")
    lateinit var pwd: String

    @Value("PBEWithSHA1AndDESede")
    lateinit var algorithm: String

    lateinit var encodeCipher: Cipher

    lateinit var decodeCipher: Cipher

    /**
     * 暂时放在内存中
     */
    final val salt: ByteArray = SecureRandom().generateSeed(8)

    val spec = PBEParameterSpec(salt, 100)

    @PostConstruct
    fun init() {
        key = SecretKeyFactory.getInstance(algorithm)
            .generateSecret(
                PBEKeySpec(pwd.toCharArray())
            )
        encodeCipher = Cipher.getInstance(algorithm).apply {
            init(Cipher.ENCRYPT_MODE, key, spec)
        }
        decodeCipher = Cipher.getInstance(algorithm)
    }

    fun encode(json: String): String = Base64.encode(encodeCipher.doFinal(json.toByteArray()))

    fun decode(base64: String): String = decodeCipher.apply {
        init(Cipher.DECRYPT_MODE, key, spec)
    }.doFinal(Base64.decode(base64)).decodeToString()

}