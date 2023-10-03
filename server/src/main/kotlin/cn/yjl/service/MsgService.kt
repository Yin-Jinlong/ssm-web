package cn.yjl.service

import cn.yjl.db.Msg

/**
 * 消息服务
 *
 * @author YJL
 */
interface MsgService : Service {

    /**
     *  获取所有消息
     */
    fun getAll(): Array<Msg>

    /**
     * 添加消息
     *
     * @param uid 用户ID
     * @param msg 消息内容
     */
    fun addMsg(uid: Int, msg: String)

    fun getMsgBefore(id: Int, count: Int):Array<Msg>

}