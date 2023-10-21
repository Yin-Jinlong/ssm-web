package cn.yjl.ssmweb.config

import cn.yjl.annotations.YamlPropertySource
import cn.yjl.security.token.TokenKey
import cn.yjl.security.token.TokenUtil
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 *
 * @author YJL
 */
@Configuration
@YamlPropertySource("classpath:application-token.yaml")
class TokenConfig {

    @Bean
    fun init(key: TokenKey): TokenConfig {
        TokenUtil.init(key)
        return this
    }

}