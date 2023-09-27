package cn.yjl.validater

import cn.yjl.resp.RespCode
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

abstract class BaseValidator<T : Annotation?> : ConstraintValidator<T, String> {

    abstract fun valid(value: String?): Boolean

    open val errorCode: RespCode=RespCode.VALIDATE_FAILED

    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        if (valid(value))
            return true
        throw ValidateException(errorCode)
    }
}