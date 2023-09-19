package cn.yjl.service.impl

import cn.yjl.db.User
import cn.yjl.db.dao.UserDao
import cn.yjl.service.BaseService
import cn.yjl.service.UserService
import org.apache.ibatis.session.SqlSession
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserServiceImpl : BaseService(), UserService {

    @Autowired
    lateinit var sqlSession: SqlSession

    override fun login(uid: Int, pwd: String): User? {
        val dao = sqlSession.getMapper(UserDao::class.java)
        return dao.getUserByUidPwd(uid, genSha512String(pwd))
    }

    override fun getUser(uid: Int): User? {
        val dao = sqlSession.getMapper(UserDao::class.java)
        return dao.getUserByUid(uid)
    }
}