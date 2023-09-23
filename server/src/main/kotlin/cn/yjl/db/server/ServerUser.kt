package cn.yjl.db.server

import cn.yjl.db.User

class ServerUser(
    val uid: Int,
    val name: String,
    val pwd: String
) {

    fun toUser(): User = User(uid, name)
}