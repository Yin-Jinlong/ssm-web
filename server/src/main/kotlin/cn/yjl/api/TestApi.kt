package cn.yjl.api

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 测试Api
 *
 * @author YJL
 */
@RestController
@RequestMapping("/api")
class TestApi {
    @RequestMapping("/test")
    fun test(): String = "Hello World"
}