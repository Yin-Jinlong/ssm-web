package cn.yjl.errorhandler

import cn.yjl.log.util.getLogger
import cn.yjl.resp.BaseRespJson
import cn.yjl.resp.ResponseJson
import cn.yjl.validater.ValidateException
import jakarta.servlet.http.HttpServletResponse
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 *  统一验证异常处理
 *
 *  @author YJL
 */
@Order(1)
@RestControllerAdvice
object ValidatedErrorHandler {

    private val LOGGER = getLogger()

    /**
     * 处理验证异常（自定义异常）
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidateException::class)
    fun handleValidationException(e: ValidateException): ResponseJson {
        LOGGER.severe(e.msg)
        return BaseRespJson(e.code, e.msg)
    }

}