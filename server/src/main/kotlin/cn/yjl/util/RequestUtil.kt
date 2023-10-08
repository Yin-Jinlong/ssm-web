package cn.yjl.util

import cn.yjl.security.token.TokenUtil
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpHeaders.AUTHORIZATION

fun HttpServletRequest.getToken(tokenUtil: TokenUtil) = getHeader(AUTHORIZATION)?.let { tokenBase64 ->
    tokenUtil.decode<Int>(tokenBase64)
}