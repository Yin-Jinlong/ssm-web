package cn.yjl.validater

import cn.yjl.resp.RespCode
import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.VALUE_PARAMETER)
@Constraint(validatedBy = [UidValidator::class])
annotation class Uid(
    val message: String = "",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<Payload>> = [],
    val code: RespCode = RespCode.VALIDATE_FAILED
) {
    companion object {
        val UidReg = Regex("\\d{6}")
    }
}

object UidValidator : BaseValidator<Uid>() {

    override fun valid(value: String?): Boolean {
        return value?.matches(Uid.UidReg) ?: false
    }
}
