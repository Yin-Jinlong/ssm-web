package cn.yjl.ssmweb.api

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 测试Api
 *
 * @author YJL
 */
@RestController
@RequestMapping("/api")
class Api {
    @RequestMapping("/test")
    fun test(): String = "Hello World"
}