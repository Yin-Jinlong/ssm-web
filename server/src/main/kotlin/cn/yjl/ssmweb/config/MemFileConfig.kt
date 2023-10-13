package cn.yjl.ssmweb.config

import cn.yjl.annotations.YamlPropertySource
import cn.yjl.io.MemFileManager
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 *
 * @author YJL
 */
@Configuration
@YamlPropertySource("classpath:application-mem-file.yaml")
class MemFileConfig {

    @Bean
    fun memFileManager(
        @Value("\${ssm-web.dir}") dir: String?
    ): MemFileManager = MemFileManager(dir ?: "./web/dist")

}