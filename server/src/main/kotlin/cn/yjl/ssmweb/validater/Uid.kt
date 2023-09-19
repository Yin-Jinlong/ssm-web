package cn.yjl.ssmweb.validater

import cn.yjl.resp.RespCode
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.VALUE_PARAMETER)
@Constraint(validatedBy = [UidValidator::class])
annotation class Uid(
    val min: Int = 3,
    val max: Int = 24,
    val message: String = "",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<Payload>> = [],
    val code: RespCode = RespCode.VALIDATE_FAILED
)

object UidValidator : ConstraintValidator<Uid, String> {

    private lateinit var uid: Uid

    override fun initialize(constraintAnnotation: Uid?) {
        uid = constraintAnnotation!!
    }

    private fun valid(value: String?): Boolean {
        val v = value?.toIntOrNull() ?: return false
        return v.toString().length in uid.min..uid.max
    }

    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        if (valid(value))
            return true
        throw ValidateException(uid.code)
    }

}
