package cn.yjl.db

import java.sql.Timestamp

data class Msg(
    val id: Int,
    val name: String,
    val uid: Int,
    val msg: String,
    val time: Timestamp
)