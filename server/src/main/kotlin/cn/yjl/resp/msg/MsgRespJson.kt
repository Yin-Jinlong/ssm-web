package cn.yjl.resp.msg

import cn.yjl.db.Msg
import cn.yjl.resp.ErrorRespJson
import cn.yjl.resp.RespCode

/**
 * 附加消息的返回
 *
 * @param data 消息集合
 *
 * @author YJL
 *
 */
class MsgRespJson(
    val data: Array<Msg>,
) : ErrorRespJson(RespCode.OK)