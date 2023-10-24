package cn.yjl.service

import cn.yjl.db.Msg
import cn.yjl.service.exception.AddMsgException
import cn.yjl.service.exception.DeleteMsgException
import cn.yjl.service.exception.ServiceException
import org.springframework.transaction.annotation.Transactional

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
    @Transactional(rollbackFor = [AddMsgException::class])
    fun addMsg(uid: Int, msg: String): Msg?

    /**
     * 获取小于id（以前的）的消息
     */
    fun getMsgBefore(id: Int, count: Int): Array<Msg>

    @Transactional(rollbackFor = [DeleteMsgException::class])
    fun deleteMsg(id: Int, uid: Int)

}