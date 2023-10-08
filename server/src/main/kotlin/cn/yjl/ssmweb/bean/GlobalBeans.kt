package cn.yjl.ssmweb.bean

import cn.yjl.annotations.YamlPropertySource
import cn.yjl.io.MemFileManager
import cn.yjl.util.registerTypeAdapter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonPrimitive
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.sql.Timestamp

/**
 * 全局单实例Bean
 *
 * @author YJL
 */
@Configuration
@YamlPropertySource("classpath:application-mem-file.yaml")
class GlobalBeans {

    companion object {
        val globalGson: Gson = GsonBuilder().registerTypeAdapter<Timestamp> { src ->
            JsonPrimitive(src?.time)
        }.create()
    }

    @Bean
    fun globalGson() = globalGson

    @Bean
    fun memFileManager(
        @Value("\${ssm-web.dir}") dir: String?
    ): MemFileManager = MemFileManager("./web/dist")

}