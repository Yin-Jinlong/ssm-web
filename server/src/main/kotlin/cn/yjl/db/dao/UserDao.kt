package cn.yjl.db.dao

import cn.yjl.annotations.ServerUse
import cn.yjl.db.User
import cn.yjl.db.server.ServerUser
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Select

interface UserDao {

    @Insert("insert into user(name,pwd) values(#{name},#{pwdsha})")
    fun logon(name: String, pwdsha: String)

    @Select("select * from user where uid = #{uid} and pwd = #{pwd}")
    fun getUserByUidPwd(uid: Int, pwd: String): User?

    @Select("select * from user where uid = #{uid}")
    fun getUserByUid(uid: Int): User?

    @ServerUse
    @Select("select * from user where name=#{name}")
    fun getUserByName(name: String): ServerUser

}