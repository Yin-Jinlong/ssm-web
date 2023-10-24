package cn.yjl.service.impl

import cn.yjl.dao.UserDao
import cn.yjl.db.User
import cn.yjl.resp.ErrorRespJson
import cn.yjl.resp.RespCode
import cn.yjl.security.sha1_512
import cn.yjl.security.token.Token
import cn.yjl.security.token.isAlive
import cn.yjl.service.BaseService
import cn.yjl.service.UserService
import cn.yjl.service.exception.ServiceException
import cn.yjl.service.exception.ServiceExceptionWithJson
import cn.yjl.service.util.assertRowChangeError
import cn.yjl.validater.Uid
import cn.yjl.validater.Uname
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Lazy
import org.springframework.dao.DuplicateKeyException
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
        val u = User(0, name, pwd.sha1_512)
        try {
            assertRowChangeError(dao.newUser(u)) {
                ServiceException("插入用户失败：$name pwd:$pwd")
            }
        } catch (e: DuplicateKeyException) {
            throw ServiceExceptionWithJson(
                "插入用户失败：$name pwd:$pwd",
                ErrorRespJson(RespCode.USER_LOGON_UNAME_EXISTS),
                e
            )
        }
        return u
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