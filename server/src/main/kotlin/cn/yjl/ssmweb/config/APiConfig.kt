package cn.yjl.ssmweb.config

import cn.yjl.interceptor.ApiInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * API 配置
 *
 * @author YJL
 */
@Configuration
class APiConfig : WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.apply {
            addInterceptor(ApiInterceptor())
                .addPathPatterns("/api/**")
        }
    }
}