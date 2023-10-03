package cn.yjl.resp.user

import cn.yjl.db.User
import cn.yjl.resp.BaseRespJson
import cn.yjl.resp.RespCode

/**
 * 用户响应
 *
 * @author YJL
 */
class UserRespJson(
    val user: User
) : BaseRespJson(RespCode.OK.code, RespCode.OK.msg)