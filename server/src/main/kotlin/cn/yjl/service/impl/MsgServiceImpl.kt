package cn.yjl.service.impl

import cn.yjl.dao.MsgDao
import cn.yjl.db.Msg
import cn.yjl.service.BaseService
import cn.yjl.service.MsgService
import cn.yjl.service.exception.AddMsgException
import cn.yjl.service.exception.DeleteMsgException
import cn.yjl.service.util.assertRowChangeError
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

    override fun addMsg(uid: Int, msg: String): Msg? {
        val m = Msg(0, "", uid, msg, Timestamp(0))
        assertRowChangeError(dao.insertMsg(m)) {
            AddMsgException("添加失败：$uid msg:$msg")
        }
        return dao.getMsgById(m.id)
    }

    override fun getMsgBefore(id: Int, count: Int): Array<Msg> {
        return dao.getMsgBeforeIdLimitCount(id, count)
    }

    override fun deleteMsg(id: Int, uid: Int) {
        assertRowChangeError(dao.delete(id, uid)) {
            DeleteMsgException("删除失败：$id uid:$uid")
        }
    }
}