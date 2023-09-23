package cn.yjl.service.impl

import cn.yjl.db.User
import cn.yjl.db.dao.UserDao
import cn.yjl.db.server.ServerException
import cn.yjl.security.sha1_512
import cn.yjl.service.BaseService
import cn.yjl.service.UserService
import org.apache.ibatis.session.SqlSession
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Service

@Service
class UserServiceImpl : BaseService(), UserService {

    @Autowired
    final lateinit var sqlSession: SqlSession


    @Autowired
    @Lazy
    lateinit var dao: UserDao

    @Bean
    fun getUserDao(): UserDao = sqlSession.getMapper(UserDao::class.java)

    override fun logon(name: String, pwd: String): User {
        dao.logon(name, pwd.sha1_512)
        val u = dao.getUserByName(name)
        if (u.pwd == pwd.sha1_512)
            return u.toUser()
        throw ServerException("插入出现未知异常")
    }

    override fun login(uid: Int, pwd: String): User? {
        return dao.getUserByUidPwd(uid, pwd.sha1_512)
    }

    override fun getUser(uid: Int): User? {
        return dao.getUserByUid(uid)
    }


}