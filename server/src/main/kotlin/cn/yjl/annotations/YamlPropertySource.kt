package cn.yjl.annotations

import cn.yjl.ssmweb.YamlPropertySourceFactory
import org.springframework.context.annotation.PropertySource
import org.springframework.core.annotation.AliasFor

/**
 * Yaml 配置源
 *
 * @author YJL
 */
@PropertySource(factory = YamlPropertySourceFactory::class)
annotation class YamlPropertySource(
    @get:AliasFor(annotation = PropertySource::class, attribute = "value")
    val value: Array<String>
)
