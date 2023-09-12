package cn.yjl.ssmweb.api

import cn.yjl.db.dao.UserDao
import cn.yjl.resp.ErrorRespJson
import cn.yjl.resp.RespCode
import cn.yjl.resp.ResponseJson
import cn.yjl.resp.user.UserLoginRespJson
import org.apache.ibatis.session.SqlSession
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Validated
@RestController
@RequestMapping("/api/user", method = [RequestMethod.POST])
class UserApi {

    @Autowired
    lateinit var sqlSession: SqlSession


    @PostMapping("/login")
    fun login(
        @RequestParam
        uid: String,
        @RequestParam
        pwd: String
    ): ResponseJson {
        val id = uid.toIntOrNull() ?: return ErrorRespJson(RespCode.USER_FAILED_LOGIN)
        val dao = sqlSession.getMapper(UserDao::class.java)
        val user = dao.getUserByUidPwd(id, pwd)
        return if (user != null)
            UserLoginRespJson(RespCode.USER_LOGIN_SUCCESS, user)
        else
            ErrorRespJson(RespCode.USER_FAILED_LOGIN)
    }

}