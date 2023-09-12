package cn.yjl.ssmweb

import cn.yjl.test.db.dao.UserDao
import org.apache.ibatis.session.SqlSession
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class MybatisTests {

    @Autowired
    lateinit var sqlSession: SqlSession

    /**
     * 测试Mybatis是否能够使用（初始）
     */
    @Test
    fun testMybatis() {
        val userDao = sqlSession.getMapper(UserDao::class.java)
        val user = userDao.getUserByName("小明")
        assert(user.name == "小明")
        assert(user.age == 18)
    }

}