package cn.yjl.security.token

import cn.yjl.annotations.YamlPropertySource
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.Key
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.PBEParameterSpec

/**
 * Token密码
 *
 * 附带Cipher实例
 * @author YJL
 */
@Component
@YamlPropertySource("classpath:application-token.yaml")
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
    val salt: ByteArray = SecureRandom().generateSeed(8)

    @PostConstruct
    fun init() {
        key = SecretKeyFactory.getInstance(algorithm)
            .generateSecret(
                PBEKeySpec(pwd.toCharArray())
            )
        val spec = PBEParameterSpec(salt, 100)
        encodeCipher = Cipher.getInstance(algorithm).apply {
            init(Cipher.ENCRYPT_MODE, key, spec)
        }
        decodeCipher = Cipher.getInstance(algorithm).apply {
            init(Cipher.DECRYPT_MODE, key, spec)
        }
    }

    fun encode(data: ByteArray): ByteArray = encodeCipher.doFinal(data)

    fun decode(data: ByteArray): ByteArray = decodeCipher.doFinal(data)

}