package cn.yjl.util

import cn.yjl.security.token.TokenUtil
import cn.yjl.security.token.isAlive
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpHeaders

fun HttpServletRequest.getToken(
    validateAlive: Boolean = true
) = TokenUtil.decode<Int>(getHeader(HttpHeaders.AUTHORIZATION))?.let {
    if (validateAlive)
        if (it.isAlive()) it else null
    else
        it
}
