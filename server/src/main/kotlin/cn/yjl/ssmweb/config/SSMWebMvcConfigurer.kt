package cn.yjl.ssmweb.config

import cn.yjl.ssmweb.SSMHandlerMethodArgumentResolver
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter

/**
 * @author YJL
 */
@Configuration
@EnableWebMvc
class SSMWebMvcConfigurer : WebMvcConfigurer {

//    override fun extendMessageConverters(converters: MutableList<HttpMessageConverter<*>>) {
//        converters.add(0, FileStreamMessageConverter())
//    }


}

/**
 *
 * @author YJL
 */
@Configuration
class SSMWebMvcArgumentResolversConfigurer {
    @Autowired
    lateinit var rmha: RequestMappingHandlerAdapter

    @PostConstruct
    fun init() {
        val l = rmha.argumentResolvers ?: arrayListOf()
        val nl = mutableListOf<HandlerMethodArgumentResolver>()
        nl.add(SSMHandlerMethodArgumentResolver())
        nl.addAll(l)
        rmha.argumentResolvers = nl
    }
}