package cn.yjl.util

import cn.yjl.security.token.TokenUtil
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpHeaders

fun HttpServletRequest.getToken() = TokenUtil.decode<Int>(getHeader(HttpHeaders.AUTHORIZATION))
