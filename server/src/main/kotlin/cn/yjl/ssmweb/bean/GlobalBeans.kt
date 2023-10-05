package cn.yjl.ssmweb.bean

import cn.yjl.annotations.YamlPropertySource
import cn.yjl.io.MemFileManager
import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * 全局单实例Bean
 *
 * @author YJL
 */
@Configuration
@YamlPropertySource(["application-mem-file.yaml"])
class GlobalBeans {

    companion object {
        val globalGson = Gson()
    }

    @Bean
    fun globalGson() = globalGson

    @Bean
    fun memFileManager(
        @Value("\${ssm-web.dir}")
        dir: String?
    ): MemFileManager = MemFileManager( "./web/dist")

}