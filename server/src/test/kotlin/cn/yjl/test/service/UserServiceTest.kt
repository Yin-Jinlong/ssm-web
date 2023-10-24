package cn.yjl.test.service

import cn.yjl.db.User
import cn.yjl.security.sha1_512
import cn.yjl.service.UserService
import cn.yjl.ssmweb.SsmWebApplication
import cn.yjl.test.annotation.SSMTest
import cn.yjl.test.util.sameAs
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import javax.sql.DataSource

@SSMTest([SsmWebApplication::class])
class UserServiceTest {

    @Autowired
    lateinit var userService: UserService

    val testUser = User(100001, "user", "123456".sha1_512)

    @Test
    fun testGetUser() {
        val user = userService.getUser(testUser.uid) ?: return assert(false)
        user sameAs testUser
    }

    @Test
    fun testUidLogin() {
        userService.login("100001", "123456") sameAs testUser
        userService.login("100001", "111111".sha1_512) sameAs null
    }

    @Test
    fun testUnameLogin() {
        userService.login("user", "123456") sameAs testUser
        userService.login("user", "111111".sha1_512) sameAs null
    }

    @Autowired
    lateinit var dataSource: DataSource

    @Test
    fun testLogon() {
        val c = dataSource.connection
        val s = c.createStatement()
        s.executeUpdate("delete from user where name='test1'")
        s.close()
        c.close()

        val r = userService.logon("test1", "666666")
        r?.name sameAs "test1"
    }

}