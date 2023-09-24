package cn.yjl.service

import cn.yjl.db.User
import cn.yjl.resp.ResponseJson
import cn.yjl.resp.user.UserLogonRespJson

interface UserService : Service {

    /**
     * 注册
     */
    fun logon(name: String, pwd: String): ResponseJson

    fun login(logid: String, pwd: String): User?

    fun getUser(uid: Int): User?

}