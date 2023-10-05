package cn.yjl.interceptor

import cn.yjl.annotations.ShouldLogin
import cn.yjl.api.uitl.getUid
import cn.yjl.api.uitl.isLogin
import cn.yjl.log.util.getLogger
import cn.yjl.resp.ErrorRespJson
import cn.yjl.resp.RespCode
import cn.yjl.ssmweb.bean.GlobalBeans
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor

/**
 * Api 拦截器
 *
 * @author YJL
 */
class ApiInterceptor : HandlerInterceptor {

    private val LOGGER = getLogger()

    private val gson = GlobalBeans.globalGson

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (handler is HandlerMethod) {
            handler.handleAnnotation<ShouldLogin> {
                val session = request.session
                LOGGER.info(session.getUid() + " >> ${request.requestURI}")
                val uid = request.getParameter("uid") ?: return false
                if (session.isLogin()) {
                    if (uid == session.getUid()) {
                        return true
                    }
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