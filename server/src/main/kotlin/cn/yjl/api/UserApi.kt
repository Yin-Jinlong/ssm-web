package cn.yjl.api

import cn.yjl.resp.ErrorRespJson
import cn.yjl.resp.RespCode
import cn.yjl.resp.ResponseJson
import cn.yjl.resp.UserRespJson
import cn.yjl.security.token.Token
import cn.yjl.security.token.TokenUtil
import cn.yjl.service.UserService
import cn.yjl.util.getToken
import cn.yjl.util.log.getLogger
import cn.yjl.validater.Logid
import cn.yjl.validater.Pwd
import cn.yjl.validater.Uid
import cn.yjl.validater.Uname
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders.AUTHORIZATION
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

    @Autowired
    private lateinit var tokenUtil: TokenUtil

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
        @Pwd
        @RequestParam
        pwd: String,
        resp: HttpServletResponse
    ): ResponseJson {
        userService.logon(uname, pwd)?.let {
            resp.saveToken(it.uid)
            return UserRespJson.logonResp(it)
        }
        resp.status = SC_BAD_REQUEST
        return ErrorRespJson(RespCode.USER_LOGON_UNAME_EXISTS)
    }

    fun HttpServletResponse.saveToken(uid: Int) {
        setHeader(AUTHORIZATION, tokenUtil.encode(Token(uid)))
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
        logid: String?,
        @RequestParam
        @Pwd
        pwd: String?,
        req: HttpServletRequest,
        resp: HttpServletResponse
    ): ResponseJson {
        val token = req.getToken(tokenUtil)

        val user = userService.loginByToken(token)?.apply {
            LOGGER.info("login by token :$uid")
        } ?: if (logid != null && pwd != null) {
            LOGGER.info("login:$logid - pwd: $pwd")
            userService.login(logid, pwd)
        } else {
            LOGGER.info("${req.remoteAddr} login failed by token")
            return ErrorRespJson(RespCode.TOKEN_ERROR)
        }

        user?.let {
            // 存到header里
            resp.saveToken(it.uid)
            return UserRespJson.loginResp(it)
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
        uid: Int,
        resp: HttpServletResponse
    ): ResponseJson {
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
