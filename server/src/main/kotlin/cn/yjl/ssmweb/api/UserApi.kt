package cn.yjl.ssmweb.api

import cn.yjl.log.util.getLogger
import cn.yjl.resp.ErrorRespJson
import cn.yjl.resp.RespCode
import cn.yjl.resp.ResponseJson
import cn.yjl.resp.user.UserLoginRespJson
import cn.yjl.resp.user.UserRespJson
import cn.yjl.service.UserService
import cn.yjl.ssmweb.validater.Logid
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Validated
@RestController
@RequestMapping("/api/user", method = [RequestMethod.POST, RequestMethod.GET])
class UserApi {

    val log = getLogger()

    @Autowired
    lateinit var userService: UserService

    @PostMapping("/logon")
    fun logon(uname: String, pwd: String, resp: HttpServletResponse): ResponseJson {
        if (uname.length !in 3..12 && uname.matches(Regex("\\d{6}")))
            return ErrorRespJson(RespCode.USER_NAME_ERROR)
        val r= userService.logon(uname, pwd)
        if (r.code!=0){
            resp.status=HttpServletResponse.SC_BAD_REQUEST
        }
        return r
    }

    @PostMapping("/login")
    fun login(
        @RequestParam
        @Logid
        logid: String,
        @RequestParam
        pwd: String,
        resp: HttpServletResponse
    ): ResponseJson {
        log.info("login:$logid - $pwd")
        val user = userService.login(logid, pwd)
        return if (user != null)
            UserLoginRespJson(RespCode.USER_LOGIN_SUCCESS, user)
        else {
            resp.status = HttpServletResponse.SC_BAD_REQUEST
            ErrorRespJson(RespCode.USER_FAILED_LOGIN)
        }
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