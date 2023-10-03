package cn.yjl.service.impl

import cn.yjl.db.Msg
import cn.yjl.db.dao.MsgDao
import cn.yjl.service.BaseService
import cn.yjl.service.MsgService
import org.apache.ibatis.session.SqlSession
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Service

/**
 * @author YJL
 */
@Service
class MsgServiceImpl : BaseService(), MsgService {

    @Autowired
    lateinit var sqlSession: SqlSession

    @Lazy
    @Autowired
    lateinit var dao: MsgDao

    @Bean
    fun getMsgDao(): MsgDao = sqlSession.getMapper(MsgDao::class.java)

    override fun getAll(): Array<Msg> {
        return dao.getAll()
    }

    override fun addMsg(uid: Int, msg: String) {
        dao.insertMsg(uid, msg)
    }
}