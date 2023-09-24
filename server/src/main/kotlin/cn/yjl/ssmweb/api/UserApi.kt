package cn.yjl.ssmweb.api

import cn.yjl.log.util.getLogger
import cn.yjl.resp.ErrorRespJson
import cn.yjl.resp.RespCode
import cn.yjl.resp.ResponseJson
import cn.yjl.resp.user.UserLoginRespJson
import cn.yjl.resp.user.UserRespJson
import cn.yjl.service.UserService
import cn.yjl.ssmweb.validater.Logid
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.servlet.http.HttpSession
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.lang.System.currentTimeMillis

@Validated
@RestController
@RequestMapping("/api/user", method = [RequestMethod.POST, RequestMethod.GET])
class UserApi {

    val log = getLogger()

    companion object {
        const val SESSION_LOGGED_TIME = "logged-time"
        const val SESSION_USER_PWD = "user-pwd"
    }

    @Autowired
    lateinit var userService: UserService

    @PostMapping("/logon")
    fun logon(uname: String, pwd: String, resp: HttpServletResponse): ResponseJson {
        if (uname.length !in 3..12 && uname.matches(Regex("\\d{6}")))
            return ErrorRespJson(RespCode.USER_NAME_ERROR)
        val r = userService.logon(uname, pwd)
        if (r.code != 0) {
            resp.status = HttpServletResponse.SC_BAD_REQUEST
        }
        return r
    }

    /**
     * 从Session获取密码
     */
    private fun HttpSession.getPwd() = getAttribute(SESSION_USER_PWD) as String?

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
        req: HttpServletRequest,
        resp: HttpServletResponse
    ): ResponseJson {
        log.info("login:$logid - $pwd")
        // 用户密码
        var upwd = pwd
        if (pwd == null) {
            val session = req.session
            // 还在时效内
            if (!session.isOutOfDate())
                upwd = session.getPwd()
        }
        upwd?.let {// 有密码
            val user = userService.login(logid, upwd)
            if (user != null) {
                // 保存一下
                req.getSession(true).apply {
                    setAttribute(SESSION_LOGGED_TIME, now())
                    setAttribute(SESSION_USER_PWD, upwd)
                }
                return UserLoginRespJson(RespCode.USER_LOGIN_SUCCESS, user)
            }
        }
        resp.status = HttpServletResponse.SC_BAD_REQUEST
        return ErrorRespJson(RespCode.USER_FAILED_LOGIN)
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

}

fun now() = currentTimeMillis()