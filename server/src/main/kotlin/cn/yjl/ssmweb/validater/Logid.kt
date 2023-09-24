package cn.yjl.ssmweb.validater

import cn.yjl.resp.RespCode
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.VALUE_PARAMETER)
@Constraint(validatedBy = [UidValidator::class])
annotation class Logid(
    val min: Int = 3,
    val max: Int = 24,
    val message: String = "",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<Payload>> = [],
    val code: RespCode = RespCode.VALIDATE_FAILED
) {
    companion object {
        val UidReg = Regex("\\d{6}")
        val UnameReg = Regex("\\S{3,12}")
    }
}

object UidValidator : ConstraintValidator<Logid, String> {

    private lateinit var logid: Logid

    override fun initialize(constraintAnnotation: Logid?) {
        logid = constraintAnnotation!!
    }

    val num=Regex("\\d+")

    private fun valid(value: String?): Boolean {
        return if (value == null) false
        else if (value.matches(Logid.UidReg)) true
        else value.matches(Logid.UnameReg)&&!value.matches(num)
    }

    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        if (valid(value))
            return true
        throw ValidateException(logid.code)
    }

}
