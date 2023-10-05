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

    companion object {
        val globalGson = Gson()
        val defaultMemFileManager = MemFileManager(System.getProperty("yjl.web-dir") ?: "./web/dist")
    }


    @Bean
    fun globalGson() = globalGson

    @Bean
    fun memFileManager(): MemFileManager = defaultMemFileManager

}