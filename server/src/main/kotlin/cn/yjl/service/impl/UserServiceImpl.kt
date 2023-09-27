package cn.yjl.service.impl

import cn.yjl.db.User
import cn.yjl.db.dao.UserDao
import cn.yjl.db.server.ServerException
import cn.yjl.resp.ErrorRespJson
import cn.yjl.resp.RespCode
import cn.yjl.resp.ResponseJson
import cn.yjl.resp.user.UserLogonRespJson
import cn.yjl.security.sha1_512
import cn.yjl.service.BaseService
import cn.yjl.service.UserService
import cn.yjl.validater.Logid
import cn.yjl.validater.Uid
import cn.yjl.validater.Uname
import org.apache.ibatis.session.SqlSession
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException

@Service
class UserServiceImpl : BaseService(), UserService {

    @Autowired
    final lateinit var sqlSession: SqlSession


    @Autowired
    @Lazy
    lateinit var dao: UserDao

    @Bean
    fun getUserDao(): UserDao = sqlSession.getMapper(UserDao::class.java)

    override fun logon(name: String, pwd: String): ResponseJson {
        if (dao.getUserByName(name) != null)
            return ErrorRespJson(RespCode.USER_LOGON_UNAME_EXISTS)
        dao.newUser(name, pwd.sha1_512)
        val u = dao.getUserByName(name) ?: throw ServerException("获取用户失败：$name")
        if (u.pwd == pwd.sha1_512)
            return UserLogonRespJson(RespCode.USER_LOGON_OK, u.toUser())
        throw ServerException("插入出现未知异常")
    }

    override fun login(logid: String, pwd: String): User? {
        return if (logid.matches(Uid.UidReg))
            dao.getUserByUidPwd(logid.toInt(), pwd.sha1_512)
        else if (logid.matches(Uname.UnameReg))
            dao.getUserByNamePwd(logid, pwd.sha1_512)
        else
            throw IllegalArgumentException("No format way to login:$logid $pwd")
    }

    override fun getUser(uid: Int): User? {
        return dao.getUserByUid(uid)
    }


}