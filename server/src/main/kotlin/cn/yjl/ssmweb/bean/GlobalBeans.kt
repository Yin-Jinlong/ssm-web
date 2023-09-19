package cn.yjl.ssmweb.bean

import cn.yjl.io.MemFileManager
import com.google.gson.Gson
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * 全局单实例Bean
 */
@Configuration
class GlobalBeans {

    val globalGson = Gson()

    @Bean
    fun globalGson() = globalGson


    val defaultMemFileManager = MemFileManager(System.getProperty("yjl.web-dir") ?: "./web/dist")

    @Bean
    fun memFileManager(): MemFileManager = defaultMemFileManager

}