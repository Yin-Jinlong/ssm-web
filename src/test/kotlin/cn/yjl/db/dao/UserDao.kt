package cn.yjl.db.dao

import cn.yjl.db.User
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface UserDao {

    @Select("select * from test where name = #{name}")
    fun getUserByName(name: String): User
}