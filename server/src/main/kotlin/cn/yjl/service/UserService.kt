package cn.yjl.service

import cn.yjl.db.User

interface UserService : Service {

    fun login(uid: Int, pwd: String): User?

    fun getUser(uid: Int): User?

}