package cn.yjl.interceptor

import cn.yjl.annotations.ShouldLogin
import cn.yjl.resp.ErrorRespJson
import cn.yjl.resp.RespCode
import cn.yjl.util.getToken
import cn.yjl.util.log.getLogger
import cn.yjl.util.now
import com.google.gson.Gson
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor

/**
 * Api 拦截器
 *
 * @author YJL
 */
@Component
class ApiInterceptor : HandlerInterceptor {

    private val LOGGER = getLogger()

    @Autowired
    private lateinit var gson: Gson

    private fun notLogin(resp: HttpServletResponse): Boolean {
        resp.status = SC_BAD_REQUEST
        resp.writer.println(gson.toJson(ErrorRespJson(RespCode.USER_NOT_LOGIN)))
        return false
    }

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (handler is HandlerMethod) {
            handler.handleAnnotation<ShouldLogin> {
                response.characterEncoding = "UTF-8"
                response.contentType = "application/json;charset=UTF-8"
                val token = request.getToken() ?: return notLogin(response)
                LOGGER.info("${token.v} >> ${request.requestURI}")
                // token
                if (now() < token.time()) {
                    return true
                }
                response.status = SC_BAD_REQUEST
                response.writer.println(gson.toJson(ErrorRespJson(RespCode.USER_NOT_LOGIN)))
                return false
            }
        }
        return true
    }

    private inline fun <reified T : Annotation> HandlerMethod.handleAnnotation(block: (anno: T) -> Unit): HandlerMethod {
        method.getAnnotation(T::class.java)?.let(block)
        return this
    }
}