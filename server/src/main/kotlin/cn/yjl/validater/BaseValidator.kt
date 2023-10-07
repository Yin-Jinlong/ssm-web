package cn.yjl.validater

import cn.yjl.resp.RespCode
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

/**
 * @author YJL
 */
abstract class BaseValidator<A : Annotation?,T> : ConstraintValidator<A, T> {

    /**
     * 是否有效
     *
     * @param value 值
     */
    abstract fun valid(value: T?): Boolean

    /**
     * 错误码
     *
     * 验证失败时返回
     */
    open val errorCode: RespCode = RespCode.VALIDATE_FAILED

    override fun isValid(value: T?, context: ConstraintValidatorContext?): Boolean {
        if (valid(value))
            return true
        throw ValidateException(errorCode)
    }
}