package cn.yjl.resp

import cn.yjl.db.User

/**
 *
 * @author YJL
 */
open class UserRespJson(
    val user: User,
    code: RespCode = RespCode.OK
) : BaseRespJson(code.code, code.msg) {

    companion object {

        fun loginResp(user: User) = UserRespJson(user, RespCode.USER_LOGIN_SUCCESS)

        fun logonResp(user: User) = UserRespJson(user, RespCode.USER_LOGIN_SUCCESS)
    }
}