package cn.yjl.db

import java.sql.Timestamp

/**
 * 消息
 *
 * @param id 消息id
 * @param name 用户名
 * @param uid 用户id
 * @param msg 消息内容
 * @param time 消息时间
 */
data class Msg(
    val id: Int,
    val name: String,
    val uid: Int,
    val msg: String,
    val time: Timestamp
)