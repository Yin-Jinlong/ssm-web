package cn.yjl.db.dao

import cn.yjl.db.User
import org.apache.ibatis.annotations.Select

interface UserDao {

    @Select("select * from user where uid = #{uid} and pwd = #{pwd}")
    fun getUserByUidPwd(uid: Int, pwd: String): User?

}