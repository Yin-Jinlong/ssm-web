package cn.yjl.service

import cn.yjl.db.User

interface UserService : Service {

    /**
     * 注册
     */
    fun logon(name: String, pwd: String): User

    fun login(uid: Int, pwd: String): User?

    fun getUser(uid: Int): User?

}