package cn.yjl.validater

import cn.yjl.resp.RespCode
import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

/**
 * 密码
 *
 * @author YJL
 */
@Target(AnnotationTarget.VALUE_PARAMETER)
@Constraint(validatedBy = [PwdValidator::class])
annotation class Pwd(
    val name: String = "pwd",
    val message: String = "",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<Payload>> = [],
    val code: RespCode = RespCode.USER_PWD_ERROR
)

/**
 * 密码验证器
 *
 * @author YJL
 */
object PwdValidator : BaseValidator<Pwd, String>() {

    override fun valid(value: String?): Boolean {
        return value?.length in 6..18
    }
}