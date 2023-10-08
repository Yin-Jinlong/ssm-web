package cn.yjl.ssmweb

import cn.yjl.resp.RespCode
import cn.yjl.validater.ValidateException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.core.MethodParameter
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

/**
 *
 * @author YJL
 */
class SSMHandlerMethodArgumentResolver : HandlerMethodArgumentResolver {

    private val supprotedTypes = arrayListOf(
        String::class.java,
        Int::class.java,
        Long::class.java,
        Float::class.java,
        Double::class.java
    )

    override fun supportsParameter(parameter: MethodParameter) = parameter.parameterType in supprotedTypes

    private fun validateFailed(name: String?): Nothing =
        throw ValidateException(RespCode.VALIDATE_FAILED.code, "参数校验失败：$name")

    private fun MethodParameter.optional(v: Any?): Any? {
        return if (isOptional) {
            v
        } else {
            if (v == null)
                validateFailed(parameterName)
            v
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
        kotlin.runCatching {
            val r = when (parameter.parameterType) {

                String::class.java -> {
                    v
                }

                Int::class.java -> {
                    v?.toIntOrNull()
                }

                Long::class.java -> {
                    v?.toLongOrNull()
                }

                Float::class.java -> {
                    v?.toFloatOrNull()
                }

                Double::class.java -> {
                    v?.toDoubleOrNull()
                }

                else -> throw RuntimeException("")
            }
            return parameter.optional(r)
        }.onFailure {
            validateFailed(parameter.parameterName)
        }
        return null
    }
}