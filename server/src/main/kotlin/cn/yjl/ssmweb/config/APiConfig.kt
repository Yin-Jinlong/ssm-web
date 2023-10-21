package cn.yjl.ssmweb.config

import cn.yjl.interceptor.ApiInterceptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * API 配置
 *
 * @author YJL
 */
@Configuration
@Component
class APiConfig : WebMvcConfigurer {

    @Autowired
    lateinit var apiInterceptor: ApiInterceptor

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.apply {
            addInterceptor(apiInterceptor)
                .addPathPatterns("/api/**")
        }
    }
}