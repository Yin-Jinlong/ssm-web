package cn.yjl.resp

/**
 * 基本消息返回
 *
 * @author YJL
 */
open class BaseRespJson(
    override var code: Int,
    override var msg: String,
) : ResponseJson