package cn.yjl.ssmweb

import cn.yjl.resp.BaseRespJson
import cn.yjl.resp.ResponseJson
import cn.yjl.ssmweb.validater.ValidateException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 *  统一验证异常处理
 */
@RestControllerAdvice
class ValidatedErrorHandler {

    /**
     * 处理验证异常（自定义异常）
     */
    @ExceptionHandler(ValidateException::class)
    fun handleValidationException(e: ValidateException): ResponseJson = BaseRespJson(e.code, e.msg)

}