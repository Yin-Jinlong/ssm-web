package cn.yjl.ssmweb

import org.springframework.boot.env.YamlPropertySourceLoader
import org.springframework.core.env.PropertySource
import org.springframework.core.io.support.EncodedResource
import org.springframework.core.io.support.PropertySourceFactory

/**
 * Yaml 配置解析工厂
 *
 * @author YJL
 */
class YamlPropertySourceFactory : PropertySourceFactory {
    override fun createPropertySource(name: String?, resource: EncodedResource): PropertySource<*> =
        YamlPropertySourceLoader().load(resource.resource.filename, resource.resource)[0]

}