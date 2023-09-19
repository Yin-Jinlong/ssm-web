package cn.yjl.ssmweb.api

import cn.yjl.log.util.getLogger
import cn.yjl.resp.ErrorRespJson
import cn.yjl.resp.RespCode
import cn.yjl.resp.ResponseJson
import cn.yjl.resp.user.UserLoginRespJson
import cn.yjl.resp.user.UserRespJson
import cn.yjl.service.UserService
import cn.yjl.ssmweb.validater.Uid
import org.apache.ibatis.session.SqlSession
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Validated
@RestController
@RequestMapping("/api/user", method = [RequestMethod.POST, RequestMethod.GET])
class UserApi {

    val log = getLogger()

    @Autowired
    lateinit var sqlSession: SqlSession

    @Autowired
    lateinit var userService: UserService

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