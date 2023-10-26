package cn.yjl.dao

import cn.yjl.annotations.ServerUse
import cn.yjl.db.User
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.SelectKey
import org.springframework.dao.DuplicateKeyException
import org.springframework.transaction.annotation.Transactional

/**
 * 用户表操作接口
 *
 * @author YJL
 */
@Mapper
@Transactional
interface UserDao : Dao {

    /**
     * 新用户
     *
     * @param user 用户
     */
    @Insert("insert into user(name, pwd) values(#{name},#{pwd})")
    @SelectKey(
        statement = ["select uid from user where name=#{name}"],
        before = false, keyProperty = "uid", keyColumn = "uid", resultType = Int::class
    )
    @Transactional(rollbackFor = [DuplicateKeyException::class])
    fun newUser(user: User): Int

    /**
     * 通过用户名和密码查询用户
     *
     * @param uid 用户id
     * @param pwd 密码（sha）
     */
    @Select("select * from user where uid = #{uid} and pwd = #{pwd}")
    fun getUserByUidPwd(uid: Int, pwd: String): User?

    /**
     *  通过用户id查询用户
     *
     * @param uid 用户名id
     */
    @Select("select * from user where uid = #{uid}")
    fun getUserByUid(uid: Int): User?

    /**
     *
     * 通过用户名和密码查询用户
     *
     * @param name 用户名
     * @param pwd 密码
     */
    @Select("select * from user where name=#{name} and pwd=#{pwd}")
    fun getUserByNamePwd(name: String, pwd: String): User?

}