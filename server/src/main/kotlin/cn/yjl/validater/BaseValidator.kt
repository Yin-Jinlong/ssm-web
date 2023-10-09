package cn.yjl.validater

import cn.yjl.log.util.getLogger
import cn.yjl.resp.RespCode
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

/**
 * @author YJL
 */
abstract class BaseValidator<A : Annotation, T> : ConstraintValidator<A, T> {

    val LOGGER = getLogger()

    /**
     * 是否有效
     *
     * @param value 值
     */
    abstract fun valid(value: T?): Boolean

    lateinit var anno: A

    override fun initialize(constraintAnnotation: A) {
        anno = constraintAnnotation
    }

    private fun <R> getOrDefault(name: String, def: R, msg: String): R {
        var r: R? = null
        runCatching {
            r = anno::class.java.getDeclaredMethod("message").invoke(anno) as R?
        }.onFailure {
            LOGGER.warning(msg)
        }
        return r ?: def
    }

    fun name() = getOrDefault("name", "", "\"'$anno' has no name\"")

    fun code() = getOrDefault("code", RespCode.VALIDATE_FAILED, "\"'$anno' has no code\"")

    fun message() = getOrDefault("message", RespCode.VALIDATE_FAILED.msg, "\"'$anno' has no message\"")
        .ifBlank { code().msg }

    override fun isValid(value: T?, context: ConstraintValidatorContext?): Boolean {
        if (valid(value))
            return true
        throw ValidateException(code().code, message() + ": ${name()}", null)
    }
}