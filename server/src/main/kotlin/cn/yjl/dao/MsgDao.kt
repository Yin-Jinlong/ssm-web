package cn.yjl.dao

import cn.yjl.annotations.ServerUse
import cn.yjl.db.Msg
import org.apache.ibatis.annotations.*
import org.springframework.stereotype.Repository

/**
 * 消息表操作接口
 *
 * @author YJL
 */
@Mapper
@Repository
interface MsgDao : Dao {

    /**
     * 插入消息
     *
     * @param uid 用户id
     * @param msg 消息内容
     */
    @Insert("insert into leave_words(uid, msg) VALUES (#{uid},#{msg})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    fun insertMsg(msg: Msg):Int

    /**
     * 获取小于id的消息count个
     */
    @Select("select id,name,uid, msg, time from user_leave_words where id < #{id} order by id desc limit #{count}")
    fun getMsgBeforeIdLimitCount(id: Int, count: Int): Array<Msg>

    @Delete("delete from leave_words where id = #{id} and uid = #{uid}")
    fun delete(id: Int, uid: Int): Int

    @ServerUse
    @Select("select id,name,uid, msg, time  from user_leave_words where id = #{id}")
    fun getMsgById(id: Int): Msg?

}