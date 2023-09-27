package cn.yjl.api

import cn.yjl.api.uitl.getUid
import cn.yjl.resp.ErrorRespJson
import cn.yjl.resp.RespCode
import cn.yjl.resp.ResponseJson
import cn.yjl.resp.msg.MsgRespJson
import cn.yjl.service.MsgService
import cn.yjl.validater.NotEmpty
import cn.yjl.validater.Uid
import jakarta.servlet.http.HttpServletResponse
import jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST
import jakarta.servlet.http.HttpSession
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

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

    @PostMapping("/send")
    fun send(
        @Uid
        @RequestParam
        uid: String,
        @NotEmpty
        @RequestParam
        msg: String,
        session: HttpSession,
        resp: HttpServletResponse
    ): ResponseJson {
        session.getUid()?.let {
            msgService.addMsg(it.toInt(), msg)
            return ErrorRespJson(RespCode.USER_MSG_SEND_OK)
        }
        resp.status = SC_BAD_REQUEST
        return ErrorRespJson(RespCode.USER_NOT_LOGIN)
    }

}