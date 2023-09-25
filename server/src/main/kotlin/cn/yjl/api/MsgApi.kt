package cn.yjl.api

import cn.yjl.resp.ResponseJson
import cn.yjl.resp.msg.MsgRespJson
import cn.yjl.service.MsgService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@Validated
@RestController
@RequestMapping("/api/msg", method = [RequestMethod.GET])
class MsgApi {

    @Autowired
    lateinit var msgService: MsgService

    @PostMapping("/all")
    fun getAll(): ResponseJson {
        val msgs = msgService.getAll()
        return MsgRespJson(msgs)
    }

}