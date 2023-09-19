package cn.yjl.ssmweb.bean

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.security.MessageDigest

@Configuration
class SecurityBeans {

    val sha512 = MessageDigest.getInstance("sha512")

    @Bean
    fun getSha512Bean(): MessageDigest = sha512

}