package cn.yjl.db.server

import cn.yjl.annotations.ServerUse
import cn.yjl.db.User

/**
 * 服务端用户
 *
 * @author YJL
 */
@ServerUse
class ServerUser(
    val uid: Int,
    val name: String,
    val pwd: String
) {
    /**
     * 转换到客户端User
     */
    fun toUser(): User = User(uid, name)
}