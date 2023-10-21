package cn.yjl.api

import cn.yjl.security.token.TokenUtil
import org.springframework.beans.factory.annotation.Autowired

/**
 *
 * @author YJL
 */
abstract class Api {

    @Autowired
    internal lateinit var tokenUtil: TokenUtil

}