package cn.yjl.api

import cn.yjl.api.uitl.*
import cn.yjl.log.util.getLogger
import cn.yjl.resp.ErrorRespJson
import cn.yjl.resp.RespCode
import cn.yjl.resp.ResponseJson
import cn.yjl.resp.user.UserLoginRespJson
import cn.yjl.resp.user.UserLogonRespJson
import cn.yjl.resp.user.UserRespJson
import cn.yjl.service.UserService
import cn.yjl.validater.Logid
import cn.yjl.validater.Uid
import cn.yjl.validater.Uname
import jakarta.servlet.http.HttpServletResponse
import jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST
import jakarta.servlet.http.HttpSession
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

/**
 * 用户接口
 *
 * @author YJL
 */
@Validated
@RestController
@RequestMapping("/api/user", method = [RequestMethod.POST, RequestMethod.GET])
class UserApi {

    private val LOGGER = getLogger()

    companion object {
        const val SESSION_USER_ID = "user-id"
        const val SESSION_USER_PWD = "user-pwd"
    }

    /**
     * 用户服务
     */
    @Autowired
    lateinit var userService: UserService

    /**
     * 注册
     *
     * @param uname 用户名
     * @param pwd 密码
     */
    @PostMapping("/logon")
    fun logon(
        @Uname
        @RequestParam
        uname: String,
        pwd: String,
        session: HttpSession,
        resp: HttpServletResponse
    ): ResponseJson {
        if (uname.matches(Regex("\\d{6}")))
            return ErrorRespJson(RespCode.USER_NAME_ERROR)
        return userService.logon(uname, pwd).apply {
            if (code == 0)
                resp.status = SC_BAD_REQUEST
            else if (this is UserLogonRespJson)
                session.save(user.uid, pwd)
        }
    }

    /**
     * 登录
     *
     * @param logid 登录的唯一标识，可以是uid或用户名
     * @param pwd 密码，为空时是保持登录状态后的请求登录
     *
     */
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
        LOGGER.info("login:$logid - pwd: $pwd")
        // 用户密码
        val upwd = pwd ?:
        // 到此logid应该是uid，且应与session的uid相同
        // 否则可能为不同用户的相同密码登录
        if (session.getUid() == logid) {
            session.getPwd()
        } else
            null

        upwd?.let {// 有密码
            userService.login(logid, upwd)?.let {
                // 保存一下
                session.save(it.uid, upwd)
                return UserLoginRespJson(RespCode.USER_LOGIN_SUCCESS, it)
            }
        }
        resp.status = SC_BAD_REQUEST
        return ErrorRespJson(RespCode.USER_FAILED_LOGIN)
    }

    /**
     * 登出
     *
     * @param uid 用户id
     */
    @PostMapping("/logout")
    fun logout(
        @Uid
        @RequestParam
        uid: String,
        session: HttpSession,
        resp: HttpServletResponse
    ): ResponseJson {
        if (uid == session.getUid()) {
            session.clearAll()
            return ErrorRespJson(RespCode.OK)
        }
        return ErrorRespJson(RespCode.NOTHING)
    }

    /**
     * 获取用户名（测试通道）
     */
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
