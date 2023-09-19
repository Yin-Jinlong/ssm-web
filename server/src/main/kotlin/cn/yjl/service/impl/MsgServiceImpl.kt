package cn.yjl.service.impl

import cn.yjl.db.Msg
import cn.yjl.db.dao.MsgDao
import cn.yjl.service.BaseService
import cn.yjl.service.MsgService
import org.apache.ibatis.session.SqlSession
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MsgServiceImpl : BaseService(), MsgService {

    @Autowired
    lateinit var sqlSession: SqlSession

    override fun getAll(): Array<Msg> {
        val dao = sqlSession.getMapper(MsgDao::class.java)
        return dao.getAll()
    }
}