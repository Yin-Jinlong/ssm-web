package cn.yjl.security.token

import cn.yjl.annotations.YamlPropertySource
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import java.security.Key
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.PBEParameterSpec

/**
 *
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


    fun newCipher(spec: PBEParameterSpec, mode: Int): Cipher {
        val c = Cipher.getInstance(algorithm)
        c.init(mode, key, spec)
        return c
    }

    @PostConstruct
    fun init() {
        key = SecretKeyFactory.getInstance(algorithm)
            .generateSecret(
                PBEKeySpec(pwd.toCharArray())
            )
    }

}