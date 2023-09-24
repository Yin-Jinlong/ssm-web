package cn.yjl.resp.user

import cn.yjl.db.User
import cn.yjl.resp.BaseRespJson
import cn.yjl.resp.RespCode

class UserLogonRespJson(
    respCode: RespCode,
    val user: User
) : BaseRespJson(respCode.code, respCode.msg)