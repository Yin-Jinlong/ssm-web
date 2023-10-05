package cn.yjl.api

import cn.yjl.annotations.ShouldLogin
import cn.yjl.api.uitl.getUid
import cn.yjl.api.uitl.updateTime
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

/**
 * 消息接口
 *
 * @author YJL
 */
@Validated
@RestController
@RequestMapping("/api/msg", method = [RequestMethod.GET])
class MsgApi {

    /**
     * 消息服务
     */
    @Autowired
    lateinit var msgService: MsgService

    /**
     * 获取所有消息
     */
    @PostMapping("/all")
    fun getAll(): ResponseJson {
        val msgs = msgService.getAll()
        return MsgRespJson(msgs)
    }

    /**
     * 向服务器发送消息
     *
     * @param uid 用户id，必须已登录
     * @param msg 消息，不能为空
     */
    @ShouldLogin
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
        msgService.addMsg(uid.toInt(), msg)
        // 有活动，更新超时
        session.updateTime()
        return ErrorRespJson(RespCode.USER_MSG_SEND_OK)
    }

    @GetMapping("/get")
    fun getMsgs(
        @RequestParam
        count: String,
        @RequestParam(required = false, defaultValue = Int.MAX_VALUE.toString())
        lastId: String,
        resp: HttpServletResponse
    ): ResponseJson {
        val msgs = msgService.getMsgBefore(lastId.toInt(), count.toInt())
        return MsgRespJson(msgs)
    }

}