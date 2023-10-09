package cn.yjl.validater

import cn.yjl.resp.RespCode
import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

/**
 * 不为空验证
 *
 * @author YJL
 */
@Target(AnnotationTarget.VALUE_PARAMETER)
@Constraint(validatedBy = [NotEmptyValidator::class])
annotation class NotEmpty(
    val name: String,
    val message: String = "不能为空",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<Payload>> = [],
    val code: RespCode = RespCode.VALIDATE_FAILED
)

/**
 * @author YJL
 */
object NotEmptyValidator : BaseValidator<NotEmpty, String>() {

    override fun valid(value: String?): Boolean {
        return !value.isNullOrBlank()
    }

}
