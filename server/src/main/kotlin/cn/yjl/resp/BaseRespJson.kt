package cn.yjl.resp

open class BaseRespJson(
    override var code: Int,
    override var msg: String,
) : ResponseJson