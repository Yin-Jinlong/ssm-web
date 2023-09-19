package cn.yjl.test.service

import cn.yjl.service.UserService
import cn.yjl.ssmweb.SsmWebApplication
import cn.yjl.test.annotation.SSMTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@SSMTest([SsmWebApplication::class])
class UserServiceTest {

    @Autowired
    lateinit var userService: UserService

    @Test
    fun testGetUser() {
        val user = userService.getUser(732418) ?: return assert(false)
        assert(user.name == "user")
        assert(user.uid == 732418)
    }

    @Test
    fun testLogin() {
        val user = userService.login(732418, "123456") ?: return assert(false)
        assert(user.name == "user")
        assert(user.uid == 732418)
    }

}