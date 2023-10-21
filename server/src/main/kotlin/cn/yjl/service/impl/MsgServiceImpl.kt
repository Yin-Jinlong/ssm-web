package cn.yjl.service.impl

import cn.yjl.dao.MsgDao
import cn.yjl.db.Msg
import cn.yjl.service.BaseService
import cn.yjl.service.MsgService
import cn.yjl.service.util.assertRowChange
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Service
import java.sql.Timestamp

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

    override fun addMsg(uid: Int, msg: String): Msg? {
        val m = Msg(0, "", uid, msg, Timestamp(0))
        assertRowChange(dao.insertMsg(m)) {
            "添加失败：$uid msg:$msg"
        }
        return dao.getMsgById(m.id)
    }

    override fun getMsgBefore(id: Int, count: Int): Array<Msg> {
        return dao.getMsgBeforeIdLimitCount(id, count)
    }

    override fun deleteMsg(id: Int, uid: Int) {
        assertRowChange(dao.delete(id, uid)) {
            "删除失败：$id uid:$uid"
        }
    }
}