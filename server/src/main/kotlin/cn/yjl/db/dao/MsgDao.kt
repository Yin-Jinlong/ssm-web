package cn.yjl.db.dao

import cn.yjl.db.Msg
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select
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
     * 查出所有消息
     *
     * @return 消息列表
     */
    @Select("select id,name,uid, msg, time  from leave_words natural join user order by time desc")
    fun getAll(): Array<Msg>

    /**
     * 插入消息
     *
     * @param uid 用户id
     * @param msg 消息内容
     */
    @Insert("insert into leave_words(uid, msg) VALUES (#{uid},#{msg})")
    fun insertMsg(uid: Int, msg: String): Int

    /**
     * 获取小于id的消息count个
     */
    @Select("select id,name,uid, msg, time from leave_words natural join user where id < #{id} order by id desc limit #{count}")
    fun getMsgBeforeIdLimitCount(id: Int, count: Int): Array<Msg>

}