package cn.yjl.validater

import cn.yjl.resp.RespCode
import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

/**
 * Logid验证
 *
 * @author YJL
 */
@Target(AnnotationTarget.VALUE_PARAMETER)
@Constraint(validatedBy = [LogidValidator::class])
annotation class Logid(
    val message: String = "",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<Payload>> = [],
    val code: RespCode = RespCode.VALIDATE_FAILED
)

/**
 * 登录id验证器
 *
 * @author YJL
 */
object LogidValidator : BaseValidator<Logid, String>() {

    override fun valid(value: String?): Boolean {
        runCatching {
            return if (value == null) false
            else UidValidator.valid(value.toInt()) || UnameValidator.valid(value)
        }.onFailure {

        }
        return false
    }

}
