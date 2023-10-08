package cn.yjl.api

import cn.yjl.annotations.ShouldLogin
import cn.yjl.resp.ErrorRespJson
import cn.yjl.resp.RespCode
import cn.yjl.resp.ResponseJson
import cn.yjl.resp.msg.MsgRespJson
import cn.yjl.service.MsgService
import cn.yjl.validater.NotEmpty
import cn.yjl.validater.Uid
import jakarta.servlet.http.HttpServletResponse
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

//    /**
//     * 获取所有消息
//     */
//    @PostMapping("/all")
//    fun getAll(): ResponseJson {
//        val msgs = msgService.getAll()
//        return MsgRespJson(msgs)
//    }

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
        uid: Int,
        @NotEmpty
        @RequestParam
        msg: String,
        session: HttpSession,
        resp: HttpServletResponse
    ): ResponseJson {
        msgService.addMsg(uid, msg)
        return ErrorRespJson(RespCode.USER_MSG_SEND_OK)
    }

    /**
     * 获取消息
     *
     * @param count 数量
     * @param lastId 上次的消息id
     */
    @GetMapping("/get")
    fun getMsgs(
        @RequestParam
        lastId: Int?,
        @RequestParam
        count: Int,
        resp: HttpServletResponse
    ): ResponseJson = MsgRespJson(msgService.getMsgBefore(lastId?: Int.MAX_VALUE, count))

}