package cn.yjl.ssmweb.config

import cn.yjl.annotations.YamlPropertySource
import com.alibaba.druid.pool.DruidDataSource
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

/**
 * 数据源配置
 *
 * @author YJL
 */
@Configuration
@ConfigurationProperties(prefix = "jdbc")
@YamlPropertySource(["application-jdbc.yaml"])
class DataSourceConfig {

    lateinit var driverClassName: String

    lateinit var url: String

    lateinit var username: String

    lateinit var password: String

    @Bean
    fun getDataSource(): DataSource = DruidDataSource().let { ds ->
        ds.setDriverClassName(this.driverClassName)
        ds.setUrl(this.url)
        ds.setUsername(this.username)
        ds.setPassword(this.password)
        ds.setMinIdle(2)
        ds.setMaxActive(10)
        ds
    }
}