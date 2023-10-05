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
    val message: String = "",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<Payload>> = [],
    val code: RespCode = RespCode.VALIDATE_FAILED
)

/**
 *
 * @author YJL
 */
object PwdValidator : BaseValidator<Pwd>() {

    override val errorCode = RespCode.USER_PWD_ERROR

    override fun valid(value: String?): Boolean {
        return value?.length in 6..18
    }
}