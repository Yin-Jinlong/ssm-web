package cn.yjl.io.df

import cn.yjl.db.User

/**
 *
 * @author YJL
 */
class DataFileGroup(
    val name: String
) {
    companion object {

        val DFG_PUBLIC = DataFileGroup("public")

        fun fromUser(user: User) = DataFileGroup(user.uid.toString())

    }
}