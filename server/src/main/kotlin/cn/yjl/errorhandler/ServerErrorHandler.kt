package cn.yjl.errorhandler

import cn.yjl.log.util.getLogger
import cn.yjl.resp.ErrorRespJson
import cn.yjl.resp.RespCode
import cn.yjl.resp.ResponseJson
import cn.yjl.util.isNull
import jakarta.servlet.http.HttpServletResponse
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 * 服务端异常处理
 *
 * @author YJL
 */
@Order(100)
@RestControllerAdvice
class ServerErrorHandler {

    private val LOGGER = getLogger()

    /**
     * 处理所有
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable::class)
    fun handleValidationException(e: Throwable, resp: HttpServletResponse): ResponseJson {
        if (e.cause.isNull())
            LOGGER.severe(e.message)
        else
            LOGGER.severe(e.message + "-> ${e.cause?.message}")
        resp.status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR
        return ErrorRespJson(RespCode.SERVER_ERROR)
    }

}