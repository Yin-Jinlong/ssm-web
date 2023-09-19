package cn.yjl.test.annotation

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.annotation.AliasFor
import kotlin.reflect.KClass

@Target(AnnotationTarget.TYPE, AnnotationTarget.CLASS)
@MustBeDocumented
@SpringBootTest
annotation class SSMTest(
    @get:AliasFor(annotation = SpringBootTest::class, attribute = "classes")
    val classes: Array<KClass<*>> = [],
)