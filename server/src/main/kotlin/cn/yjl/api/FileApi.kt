package cn.yjl.api

import cn.yjl.io.df.FileStream
import cn.yjl.validater.Filename
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 *
 * @author YJL
 */
@Validated
@RestController
@RequestMapping("/api/file")
class FileApi {

    @GetMapping("get")
    fun get(
        @Filename
        @RequestParam
        file: String
    ): FileStream {
        val bs = "666".byteInputStream()
        return FileStream("$file.txt", bs)
    }

}