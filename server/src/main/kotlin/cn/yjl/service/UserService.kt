package cn.yjl.service

import cn.yjl.db.User
import cn.yjl.security.token.Token
import cn.yjl.service.exception.ServiceException
import org.springframework.transaction.annotation.Transactional

/**
 * 用户服务
 *
 * @author YJL
 */
@Transactional
interface UserService : Service {

    /**
     * 注册
     *
     * @param name 用户名
     * @param pwd 密码
     */
    @Transactional(rollbackFor = [ServiceException::class])
    fun logon(name: String, pwd: String): User?

    /**
     * 登录
     *
     * @param logid 登录唯一id
     * @param pwd 密码
     */
    fun login(logid: String, pwd: String): User?

    fun loginByToken(token: Token<Int>): User?

    /**
     * 获取用户
     *
     * @param uid 用户id
     */
    fun getUser(uid: Int): User?

}