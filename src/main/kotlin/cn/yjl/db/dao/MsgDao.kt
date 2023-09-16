package cn.yjl.db.dao

import cn.yjl.db.Msg
import org.apache.ibatis.annotations.Select

interface MsgDao {

    @Select("select id,name,uid, msg, time  from leave_words natural join user order by time desc")
    fun getAll():Array<Msg>
}