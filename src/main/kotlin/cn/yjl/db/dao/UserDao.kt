package cn.yjl.db.dao

import cn.yjl.db.User
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

/**
 * 用户数据访问层
 */
@Mapper
interface UserDao {

    @Select("select * from user where uid = #{uid} & pwd=#{pwd}")
    fun getUserByUidPwd(uid: Int, pwd: String): User?

}