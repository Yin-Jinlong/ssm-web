package cn.yjl.ssmweb.api

import cn.yjl.log.util.getLogger
import cn.yjl.resp.ErrorRespJson
import cn.yjl.resp.RespCode
import cn.yjl.resp.ResponseJson
import cn.yjl.resp.user.UserLoginRespJson
import cn.yjl.resp.user.UserRespJson
import cn.yjl.service.UserService
import cn.yjl.ssmweb.validater.Uid
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
    fun logon(name: String, pwd: String): ResponseJson {
        if (name.length !in 3..12 && name.matches(Regex("\\d{6}")))
            return ErrorRespJson(RespCode.USER_NAME_ERROR)
        if (pwd.length !in 6..18)
            return ErrorRespJson(RespCode.USER_PWD_ERROR)
        userService.logon(name, pwd)
        return ErrorRespJson(0, "注册成功")
    }

    @PostMapping("/login")
    fun login(
        @RequestParam
        @Uid
        uid: String,
        @RequestParam
        pwd: String
    ): ResponseJson {
        log.info("login:$uid - $pwd")
        val user = userService.login(uid.toInt(), pwd)
        return if (user != null)
            UserLoginRespJson(RespCode.USER_LOGIN_SUCCESS, user)
        else
            ErrorRespJson(RespCode.USER_FAILED_LOGIN)
    }

    @GetMapping("/get")
    fun getUserName(
        @RequestParam
        @Uid
        uid: String
    ): ResponseJson {
        val user = userService.getUser(uid.toInt())
        return if (user != null)
            UserRespJson(user)
        else
            ErrorRespJson(RespCode.USER_NOT_FOUND)
    }

}