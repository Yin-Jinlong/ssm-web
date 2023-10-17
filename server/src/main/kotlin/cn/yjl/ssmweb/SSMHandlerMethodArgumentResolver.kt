package cn.yjl.ssmweb

import cn.yjl.resp.RespCode
import cn.yjl.validater.ValidateException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.core.MethodParameter
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import java.math.BigDecimal
import java.math.BigInteger
import java.sql.Timestamp
import java.util.*
import kotlin.reflect.KClass

/**
 *
 * @author YJL
 */
open class SSMHandlerMethodArgumentResolver : HandlerMethodArgumentResolver {

    private val supportedTypes = arrayListOf(
        String::class.java,
        Boolean::class.java,
        Byte::class.java,
        UByte::class.java,
        Char::class.java,
        Short::class.java,
        UShort::class.java,
        Int::class.java,
        UInt::class.java,
        Long::class.java,
        ULong::class.java,
        Float::class.java,
        Double::class.java,

        BigInteger::class.java,
        BigDecimal::class.java,

        Timestamp::class.java
    )

    override fun supportsParameter(parameter: MethodParameter) =
        parameter.hasParameterAnnotation(RequestParam::class.java) && parameter.parameterType in supportedTypes

    private fun validateFailed(name: String?): Nothing =
        throw ValidateException(RespCode.VALIDATE_FAILED.code, "参数校验失败：$name")

    private fun MethodParameter.optional(v: Any?) =
        if (!isOptional && v == null) validateFailed(parameterName)
        else v

    internal fun KClass<*>.parseParam(v: String?): Any? {
        if (v == null) return null
        return when (this) {
            String::class -> v

            Boolean::class -> when (v.lowercase(Locale.getDefault())) {
                "true", "1" -> true
                "false", "0" -> false
                else -> null
            }

            Byte::class -> v.toByteOrNull()

            UByte::class -> v.toUByteOrNull()

            Char::class -> if (v.length == 1) v[0] else null

            Short::class -> v.toShortOrNull()
            UShort::class -> v.toUShortOrNull()

            Int::class -> v.toIntOrNull()
            UInt::class -> v.toUIntOrNull()

            Long::class -> v.toLongOrNull()
            ULong::class -> v.toULongOrNull()

            Float::class -> v.toFloatOrNull()
            Double::class -> v.toDoubleOrNull()

            BigInteger::class -> runCatching { BigInteger(v) }.getOrNull()
            BigDecimal::class -> runCatching { BigDecimal(v) }.getOrNull()

            Timestamp::class -> {
                v.toLongOrNull()?.let {
                    Timestamp(it)
                }
            }

            else -> throw RuntimeException("参数类型不支持: $this")
        }
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any? {
        val req = webRequest.nativeRequest as HttpServletRequest
        val v: String? = req.getParameter(parameter.parameterName)
        return runCatching {
            return@runCatching parameter.optional(parameter.parameterType.kotlin.parseParam(v))
        }.onFailure {
            validateFailed(parameter.parameterName)
        }.getOrNull()
    }
}