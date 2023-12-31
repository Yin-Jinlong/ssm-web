package cn.yjl.validater

import cn.yjl.resp.RespCode
import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

/**
 * 用户名验证
 *
 * @author YJL
 */
@Target(AnnotationTarget.VALUE_PARAMETER)
@Constraint(validatedBy = [UnameValidator::class])
annotation class Uname(
    val name: String = "name",
    val message: String = "",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<Payload>> = [],
    val code: RespCode = RespCode.USER_NAME_ERROR
) {
    companion object {
        val UnameReg = Regex("\\S{1,12}")
    }
}

/**
 * 用户名验证器
 *
 * @author YJL
 */
object UnameValidator : BaseValidator<Uname, String>() {

    private val num = Regex("\\d+")

    override fun valid(value: String?): Boolean {
        return if (value == null || value.matches(num) || value.matches(Uid.UidReg)) false
        else value.matches(Uname.UnameReg)
    }
}
