package cn.yjl.ssmweb.config

import cn.yjl.hmc.FileStreamMessageConverter
import cn.yjl.ssmweb.SSMHandlerMethodArgumentResolver
import com.google.gson.Gson
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.GsonHttpMessageConverter
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

    @Autowired
    lateinit var gson: Gson

    override fun extendMessageConverters(converters: MutableList<HttpMessageConverter<*>>) {
        converters.forEach {
            if (it is GsonHttpMessageConverter)
                it.gson = gson
        }
        converters.add(0, FileStreamMessageConverter())
    }

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