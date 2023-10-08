package cn.yjl.validater

import cn.yjl.resp.RespCode
import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

/**
 * Uid验证
 *
 * @author YJL
 */
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

/**
 * 用户id验证器
 *
 * @author YJL
 */
object UidValidator : BaseValidator<Uid,Int>("uid") {

    override fun valid(value: Int?): Boolean {
        return value?.toString()?.matches(Uid.UidReg) ?: false
    }
}
