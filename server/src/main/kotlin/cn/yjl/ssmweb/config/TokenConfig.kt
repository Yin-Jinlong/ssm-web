package cn.yjl.ssmweb.config

import cn.yjl.annotations.YamlPropertySource
import cn.yjl.security.token.TokenKey
import cn.yjl.security.token.TokenUtil
import org.springframework.beans.factory.annotation.Value
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
    fun init(
        key: TokenKey,
        @Value("\${token.alive-time}")
        aliveTime: String
    ): TokenConfig {
        TokenUtil.tokenKey = key
        TokenUtil.DefaultAliveTime = parseAliveTime(aliveTime)
        return this
    }


    companion object {

        val AliveTimeReg = Regex("(\\d+h)?(\\d+m)?(\\d+s)?")

        private fun badAliveTime(t: String): Nothing = throw IllegalArgumentException("Bad alive time:$t")

        fun parseAliveTime(t: String?): Long {
            if (t.isNullOrBlank())
                return 30 * 60 * 1000L
            val gs = AliveTimeReg.find(t)?.groups ?: badAliveTime(t)
            if (gs.count { it == null } == 3)
                badAliveTime(t)
            val hs = gs[1]?.value ?: "0h"
            val ms = gs[2]?.value ?: "0m"
            val ss = gs[3]?.value ?: "0s"
            val h = hs.substring(0, hs.length - 1).toLong()
            val m = ms.substring(0, ms.length - 1).toLong()
            val s = ss.substring(0, ss.length - 1).toLong()
            return h * 3600_000L + m * 60_000L + s * 1000L
        }
    }

}