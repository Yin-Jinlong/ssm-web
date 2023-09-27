package cn.yjl.db.dao

import cn.yjl.db.Msg
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Select

/**
 * 消息表操作接口
 */
interface MsgDao {

    @Select("select id,name,uid, msg, time  from leave_words natural join user order by time desc")
    fun getAll():Array<Msg>

    @Insert("insert into leave_words(uid, msg) VALUES (#{uid},#{msg})")
    fun insertMsg(uid:Int,msg:String)

}