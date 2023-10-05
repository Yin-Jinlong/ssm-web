package cn.yjl.service.impl

import cn.yjl.db.Msg
import cn.yjl.db.dao.MsgDao
import cn.yjl.service.BaseService
import cn.yjl.service.MsgService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Service

/**
 * @author YJL
 */
@Service
class MsgServiceImpl : BaseService(), MsgService {

    @Lazy
    @Autowired
    lateinit var dao: MsgDao

    override fun getAll(): Array<Msg> {
        return dao.getAll()
    }

    override fun addMsg(uid: Int, msg: String) {
        dao.insertMsg(uid, msg)
    }

    override fun getMsgBefore(id: Int, count: Int): Array<Msg> {
        return dao.getMsgBeforeIdLimitCount(id, count)
    }
}