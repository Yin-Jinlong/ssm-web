package cn.yjl.test.service

import cn.yjl.db.User
import cn.yjl.resp.user.UserLogonRespJson
import cn.yjl.security.sha1_512
import cn.yjl.service.UserService
import cn.yjl.ssmweb.SsmWebApplication
import cn.yjl.test.annotation.SSMTest
import cn.yjl.test.util.sameAs
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@SSMTest([SsmWebApplication::class])
class UserServiceTest {

    @Autowired
    lateinit var userService: UserService

    val testUser = User(732418, "user", "123456".sha1_512)

    @Test
    fun testGetUser() {
        val user = userService.getUser(732418) ?: return assert(false)
        user sameAs testUser
    }

    @Test
    fun testUidLogin() {
        userService.login("732418", "123456") sameAs testUser
    }

    @Test
    fun testUnameLogin() {
        userService.login("user", "123456") sameAs testUser
    }

    @Test
    fun testLogon() {
        // TODO 使用前请先删除原有用户!!!
        val r = userService.logon("test1", "666666")
        if (r is UserLogonRespJson) {
            r.user.name sameAs "test1"
        } else
            throw IllegalStateException("logon failed")
    }

}