package cn.yjl.errorhandler

import cn.yjl.resp.ErrorRespJson
import cn.yjl.resp.RespCode
import cn.yjl.resp.ResponseJson
import cn.yjl.service.exception.AddMsgException
import cn.yjl.service.exception.DeleteMsgException
import cn.yjl.service.exception.ServiceException
import cn.yjl.service.exception.ServiceExceptionWithJson
import cn.yjl.util.log.getLogger
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
    fun handleAllException(e: Throwable, resp: HttpServletResponse): ResponseJson {
        if (e.cause == null)
            LOGGER.severe(e.message)
        else
            LOGGER.severe(e.message + "-> ${e.cause?.message}")
        LOGGER.severe(e.stackTraceToString())
        resp.status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR
        return ErrorRespJson(RespCode.SERVER_ERROR)
    }

    private fun serverError(msg: String) = ErrorRespJson(RespCode.SERVER_ERROR.code, msg)

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ServiceException::class)
    fun handleServiceException(e: ServiceException, resp: HttpServletResponse): ResponseJson {
        LOGGER.info(e.message)
        return when (e) {
            is AddMsgException -> serverError("添加失败！")

            is DeleteMsgException -> serverError("删除失败！")

            is ServiceExceptionWithJson -> e.json

            else -> handleAllException(e, resp)
        }
    }

}