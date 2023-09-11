package cn.yjl.ssmweb.bean

import cn.yjl.io.MemFileManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 *  文件Bean
 */
@Configuration
class FileBean {

    val mfm = MemFileManager(System.getProperty("yjl.web-dir"))

    @Bean
    fun getMemFileManager(): MemFileManager = mfm

}