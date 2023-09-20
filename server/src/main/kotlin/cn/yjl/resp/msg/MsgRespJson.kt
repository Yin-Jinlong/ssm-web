package cn.yjl.resp.msg

import cn.yjl.db.Msg
import cn.yjl.resp.ErrorRespJson
import cn.yjl.resp.RespCode

class MsgRespJson(
    val data: Array<Msg>,
) : ErrorRespJson(RespCode.OK)