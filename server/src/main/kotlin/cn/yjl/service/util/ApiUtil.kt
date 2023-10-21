package cn.yjl.service.util

import cn.yjl.api.Api
import cn.yjl.util.getToken
import jakarta.servlet.http.HttpServletRequest

fun Api.getToken(req: HttpServletRequest) = req.getToken(tokenUtil)