package cn.yjl.service

import cn.yjl.db.Msg

interface MsgService : Service {

    fun getAll(): Array<Msg>

}