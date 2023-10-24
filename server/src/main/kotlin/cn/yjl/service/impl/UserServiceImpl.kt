package cn.yjl.service.impl

import cn.yjl.db.User
import cn.yjl.dao.UserDao
import cn.yjl.security.sha1_512
import cn.yjl.security.token.Token
import cn.yjl.security.token.isAlive
import cn.yjl.service.BaseService
import cn.yjl.service.exception.ServiceException
import cn.yjl.service.UserService
import cn.yjl.validater.Uid
import cn.yjl.validater.Uname
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Service

/**
 * @author YJL
 */
@Service
class UserServiceImpl : BaseService(), UserService {

    @Lazy
    @Autowired
    lateinit var dao: UserDao

    override fun logon(name: String, pwd: String): User? {
        if (dao.newUser(name, pwd.sha1_512) == 0)
            throw ServiceException("插入用户失败：$name pwd:$pwd")
        val u = dao.getUserByName(name) ?: throw ServiceException("获取用户失败：$name")
        if (u.pwd == pwd.sha1_512)
            return u
        throw ServiceException("插入出现未知异常")
    }

    override fun login(logid: String, pwd: String): User? {
        return if (logid.matches(Uid.UidReg))
            dao.getUserByUidPwd(logid.toInt(), pwd.sha1_512)
        else if (logid.matches(Uname.UnameReg))
            dao.getUserByNamePwd(logid, pwd.sha1_512)
        else
            throw IllegalArgumentException("No format way to login:$logid $pwd")
    }

    override fun loginByToken(token: Token<Int>): User? {
        if (!token.isAlive())
            return null
        return dao.getUserByUid(token.v)
    }

    override fun getUser(uid: Int): User? {
        return dao.getUserByUid(uid)
    }


}