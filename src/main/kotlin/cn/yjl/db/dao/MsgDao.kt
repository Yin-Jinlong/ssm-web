package cn.yjl.db.dao

import cn.yjl.db.Msg
import org.apache.ibatis.annotations.Select

interface MsgDao {

    @Select("select * from leave_words order by time desc")
    fun getAll():Array<Msg>
}