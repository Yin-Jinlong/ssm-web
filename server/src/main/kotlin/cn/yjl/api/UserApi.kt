package cn.yjl.api

import cn.yjl.log.util.getLogger
import cn.yjl.resp.ErrorRespJson
import cn.yjl.resp.RespCode
import cn.yjl.resp.ResponseJson
import cn.yjl.resp.user.UserLoginRespJson
import cn.yjl.resp.user.UserLogonRespJson
import cn.yjl.resp.user.UserRespJson
import cn.yjl.service.UserService
import cn.yjl.validater.Logid
import jakarta.servlet.http.HttpServletResponse
import jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST
import jakarta.servlet.http.HttpSession
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.lang.System.currentTimeMillis

@Validated
@RestController
@RequestMapping("/api/user", method = [RequestMethod.POST, RequestMethod.GET])
class UserApi {
    companion object {
        val LOGGER = getLogger()
        const val SESSION_LOGGED_TIME = "logged-time"
        const val SESSION_USER_ID = "user-id"
        const val SESSION_USER_PWD = "user-pwd"
    }

    @Autowired
    lateinit var userService: UserService

    @PostMapping("/logon")
    fun logon(
        uname: String, pwd: String,
        session: HttpSession,
        resp: HttpServletResponse
    ): ResponseJson {
        if (uname.length !in 3..12 && uname.matches(Regex("\\d{6}")))
            return ErrorRespJson(RespCode.USER_NAME_ERROR)
        val r = userService.logon(uname, pwd)
        if (r.code != 0) {
            resp.status = SC_BAD_REQUEST
        } else if (r is UserLogonRespJson) {
            session.save(r.user.uid, pwd)
        }
        return r
    }

    /**
     * 从Session获取密码
     */
    private fun HttpSession.getPwd() = getAttribute(SESSION_USER_PWD) as String?

    /**
     * 从Session获取用户名
     */
    private fun HttpSession.getUid() = getAttribute(SESSION_USER_ID) as String?

    private fun HttpSession.isOutOfDate(): Boolean {
        val time: Long? = getAttribute(SESSION_LOGGED_TIME) as Long?
        return time == null || now() - time > 5 * 60 * 1000L
    }

    @PostMapping("/login")
    fun login(
        @RequestParam
        @Logid
        logid: String,
        @RequestParam
        pwd: String?,
        session: HttpSession,
        resp: HttpServletResponse
    ): ResponseJson {
        LOGGER.info("login:$logid - $pwd")
        // 用户密码
        var upwd = pwd
        if (pwd == null) {
            // 还在时效内
            if (!session.isOutOfDate()) {
                // 到此logid应该是uid，且应与session的uid相同
                // 否则可能为不同用户的相同密码登录
                if (session.getUid() == logid) {
                    upwd = session.getPwd()
                }
            }
        }
        upwd?.let {// 有密码
            val user = userService.login(logid, upwd)
            if (user != null) {
                // 保存一下
                session.save(user.uid, upwd)
                return UserLoginRespJson(RespCode.USER_LOGIN_SUCCESS, user)
            }
        }
        resp.status = HttpServletResponse.SC_BAD_REQUEST
        return ErrorRespJson(RespCode.USER_FAILED_LOGIN)
    }

    /**
     * 登出
     */
    @PostMapping("/logout")
    fun logout(uid: String, session: HttpSession, resp: HttpServletResponse): ResponseJson {
        if (!uid.matches(Logid.UidReg))
            return ErrorRespJson(RespCode.VALIDATE_FAILED)
        if (uid == session.getUid()) {
            session.clearAll()
            return ErrorRespJson(RespCode.OK)
        }
        return ErrorRespJson(RespCode.NOTHING)
    }

    @GetMapping("/get")
    fun getUserName(
        @RequestParam
        @Logid
        logid: String
    ): ResponseJson {
        val user = userService.getUser(logid.toInt())
        return if (user != null)
            UserRespJson(user)
        else
            ErrorRespJson(RespCode.USER_NOT_FOUND)
    }

    fun now() = currentTimeMillis()

    fun HttpSession.save(uid: Int, pwd: String) {
        setAttribute(SESSION_LOGGED_TIME, now())
        setAttribute(SESSION_USER_ID, uid.toString())
        setAttribute(SESSION_USER_PWD, pwd)
    }


    /**
     * 清除所有保存信息
     */
    fun HttpSession.clearAll() {
        removeAttribute(SESSION_USER_ID)
        removeAttribute(SESSION_USER_PWD)
        removeAttribute(SESSION_LOGGED_TIME)
    }

}
