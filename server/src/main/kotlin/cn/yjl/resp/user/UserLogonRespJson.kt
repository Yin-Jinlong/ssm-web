package cn.yjl.resp.user

import cn.yjl.db.User
import cn.yjl.resp.BaseRespJson
import cn.yjl.resp.RespCode

/**
 * 用户注册响应
 *
 * @param user 用户
 *
 * @author YJL
 */
class UserLogonRespJson(
    respCode: RespCode,
    val user: User
) : BaseRespJson(respCode.code, respCode.msg)