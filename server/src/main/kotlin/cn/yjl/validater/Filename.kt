package cn.yjl.validater

import cn.yjl.resp.RespCode
import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

/**
 *
 * @author YJL
 */
@Target(AnnotationTarget.VALUE_PARAMETER)
@Constraint(validatedBy = [FilenameValidator::class])
annotation class Filename(
    val name: String = "",
    val message: String = "",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<Payload>> = [],
    val code: RespCode = RespCode.VALIDATE_FAILED
)

/**
 *
 * @author YJL
 */
object FilenameValidator : BaseValidator<Filename, String>() {

    override fun valid(value: String?): Boolean {
        return !value.isNullOrBlank() && value.trim().length < 32
    }
}
