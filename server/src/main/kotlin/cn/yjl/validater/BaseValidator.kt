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

    private inline fun <reified R> getOrDefault(name: String, def: R) = runCatching {
        return@runCatching (anno::class.java.getDeclaredMethod(name).invoke(anno) as R?) ?: def
    }.getOrElse {
        LOGGER.warning("'${anno.javaClass.simpleName}' has no $name")
        def
    }

    private fun name() = getOrDefault("name", "")

    private fun code() = getOrDefault("code", RespCode.VALIDATE_FAILED)

    private fun message() = getOrDefault("message", RespCode.VALIDATE_FAILED.msg)
        .ifEmpty { code().msg }// 没有的话从code中取

    override fun isValid(value: T?, context: ConstraintValidatorContext?): Boolean {
        if (value == null || valid(value)) return true
        throw ValidateException(code().code, message() + ": ${name()}", null)
    }
}