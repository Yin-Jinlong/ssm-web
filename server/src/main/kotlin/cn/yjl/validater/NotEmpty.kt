package cn.yjl.validater

import cn.yjl.resp.RespCode
import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.VALUE_PARAMETER)
@Constraint(validatedBy = [NotEmptyValidator::class])
annotation class NotEmpty(
    val message: String = "",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<Payload>> = [],
    val code: RespCode = RespCode.VALIDATE_FAILED
)

object NotEmptyValidator : BaseValidator<NotEmpty>() {

    override fun valid(value: String?): Boolean {
        return !value.isNullOrBlank()
    }
}
