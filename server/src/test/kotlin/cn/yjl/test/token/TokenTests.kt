package cn.yjl.test.token

import cn.yjl.security.token.Token
import cn.yjl.security.token.TokenUtil
import cn.yjl.ssmweb.SsmWebApplication
import cn.yjl.test.annotation.SSMTest
import cn.yjl.test.util.sameAs
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

/**
 *
 * @author YJL
 */
@SSMTest([SsmWebApplication::class])
class TokenTests {

    @Autowired
    lateinit var tokenUtil: TokenUtil

    @Test
    fun testEncodeDecode() {
        val tokenObj = 666
        val src = Token(tokenObj)
        val token = tokenUtil.encode(src)
        println(token)
        val r = tokenUtil.decode<Int>(token)
        println(r)
        r sameAs src
    }

}