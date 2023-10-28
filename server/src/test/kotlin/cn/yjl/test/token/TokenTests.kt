package cn.yjl.test.token

import cn.yjl.security.token.Token
import cn.yjl.security.token.TokenUtil
import cn.yjl.ssmweb.SsmWebApplication
import cn.yjl.ssmweb.config.TokenConfig
import cn.yjl.test.annotation.SSMTest
import cn.yjl.test.util.sameAs
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.lang.IllegalArgumentException

/**
 *
 * @author YJL
 */
@SSMTest([SsmWebApplication::class])
class TokenTests {

    @Test
    fun testEncodeDecode() {
        val tokenObj = 666
        val src = Token(tokenObj)
        val token = TokenUtil.encode(src)
        println(token)
        val r = TokenUtil.decode<Int>(token)
        println(r)
        r sameAs src
    }

    @Test
    fun testParseAliveTime() {
        TokenConfig.parseAliveTime("10m") sameAs 600000L
        Assertions.assertThrowsExactly(IllegalArgumentException::class.java) {
            TokenConfig.parseAliveTime("10")
        }
    }
}